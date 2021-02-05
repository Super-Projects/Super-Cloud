package de.z1up.supercloud.cloud.server;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.interfaces.IServer;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.bson.Document;

import java.util.logging.Filter;

public abstract class Server implements IServer {

    private UID uid;
    private ServerType serverType;
    private ServerMode serverMode;
    private String display;
    private Group group;
    private boolean maintenance;
    private int id;
    private String path;

    private int port;
    private int maxPlayers;
    private String motd;

    public Server(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path, int port, int maxPlayers, String motd) {
        this.uid = uid;
        this.serverType = serverType;
        this.serverMode = serverMode;
        this.display = display;
        this.group = group;
        this.maintenance = maintenance;
        this.id = id;
        this.path = path;
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.motd = motd;
    }

    public UID getUid() {
        return uid;
    }

    public void setUid(UID uid) {
        this.uid = uid;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public ServerMode getServerMode() {
        return serverMode;
    }

    public void setServerMode(ServerMode serverMode) {
        this.serverMode = serverMode;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    @Override
    public void save() {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        Document document
                = collection.find(Filters.eq("uid", Document.parse("{ tag: \"" + this.uid.getTag() + "\", type: \"" + this.uid.getType() + "\" }"))).first();

        if(document != null) {
            this.update0(collection);
        } else {
            document = Document.parse(new Gson().toJson(this));
            collection.insertOne(document);
        }

    }

    @Override
    public void update() {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        update0(collection);

    }

    private void update0(final MongoCollection collection) {
        //FindPublisher<Document> findPublisher = collection.find(eq("size", Document.parse("{ h: 14, w: 21, uom: 'cm' }")));
        //collection.find(Filters.eq("uid.tag", Document.parse("{ tag: \"" + this.uid.getTag() + "\", type: \"" + this.uid.getType() + "\" }")));

        collection.updateOne(Filters.eq("uid", Document.parse("{ tag: \"" + this.uid.getTag() + "\", type: \"" + this.uid.getType() + "\" }")), Document.parse(new Gson().toJson(this)));
    }
}
