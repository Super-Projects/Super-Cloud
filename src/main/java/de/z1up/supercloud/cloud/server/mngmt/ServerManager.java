package de.z1up.supercloud.cloud.server.mngmt;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.obj.GameServer;
import de.z1up.supercloud.cloud.server.obj.Server;
import de.z1up.supercloud.cloud.server.obj.Template;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.file.Copier;
import de.z1up.supercloud.core.mongo.MongoUtils;
import org.bson.Document;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;

public class ServerManager extends MongoUtils {

    private final String PATH = "local//lib//";
    private final String PROXY_FILE = "proxy.jar";
    private final String SERVER_FILE = "server.jar";

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

}
