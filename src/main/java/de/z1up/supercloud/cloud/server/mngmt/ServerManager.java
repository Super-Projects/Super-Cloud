package de.z1up.supercloud.cloud.server.mngmt;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.cloud.server.obj.GameServer;
import de.z1up.supercloud.cloud.server.obj.ProxyServer;
import de.z1up.supercloud.cloud.server.obj.Server;
import de.z1up.supercloud.cloud.server.obj.Template;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.file.Copier;
import de.z1up.supercloud.core.mongo.MongoUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;

public class ServerManager extends MongoUtils {

    private final String PATH           = "local//lib//";
    private final String PROXY_FILE     = "proxy.jar";
    private final String SERVER_FILE    = "server.jar";

    public void killOldServers() {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        final Gson gson = new Gson();

        final Document query = new Document();
        query.append("connected", true);

        final List<Document> documents
                = selectDocuments(collection, query);

        if(documents == null) {
            return;
        }

        if(documents.isEmpty()) {
            return;
        }

        documents.forEach(document -> killOldServer(document, gson));

    }

    public void killOldServer(final Document document, final Gson gson) {

        if(((String) document.get("serverType")).equalsIgnoreCase("SERVER")) {

            final GameServer server
                    = gson.fromJson(document.toJson(), GameServer.class);

            Cloud.getInstance().getLogger()
                    .debug("Found an old server running at port "
                            + server.getPort() + "! Trying to close process!");
            server.destroy();

        }

    }

    private <T extends Server, K> List<Server> getServiceByGroup0(final Class<? extends T> type,
                                                                           final Bson query,
                                                                           final MongoCollection collection) {

        final List<Server> servers
                = new ArrayList<>();

        List<Document> documents
                = super.selectDocuments(collection, query);

        if(documents == null) {
            return servers;
        }

        if(documents.isEmpty()) {
            return servers;
        }

        final Gson gson = new Gson();

        for (Document document : documents) {

            final T server
                    = gson.fromJson(document.toJson(),
                    type);
            servers.add(server);

        }

        return servers;

    }

    public List<Server> getGroupProxy(Group group) {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        final Document query
                = new Document();

        query.append("group.uid.tag",
                group.getUniqueID().getTag());

        return this.getServiceByGroup0(ProxyServer.class, query, collection);
    }

    public List<Server> getGroupServers(Group group) {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        final Document query
                = new Document();

        query.append("group.uid.tag",
                group.getUniqueID().getTag());

        return this.getServiceByGroup0(GameServer.class, query, collection);
    }

    public boolean existsService(final String name) {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        final Document query
                = new Document();
        query.append("display", name);

        return super.exists(collection, query);

    }

    public ServerType getServerTypeFromDoc(Document document) {
        final String type = (String) document.get("serverType");
        return ServerType.valueOf(type);
    }

    public Document getAsDocument(final String name) {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        final Document query
                = new Document();
        query.append("display", name);

        final Document document
                = super.selectFirstDocument(collection, query);

        return document;
    }

    public GameServer getFirstServer(final String name) {

        final Document document
                = this.getAsDocument(name);

        GameServer server = new Gson().fromJson(document.toJson(), GameServer.class);

        return server;
    }

    public ProxyServer getFirstProxy(final String name) {

        final Document document
                = this.getAsDocument(name);

        ProxyServer server = new Gson().fromJson(document.toJson(), ProxyServer.class);

        return server;
    }

}
