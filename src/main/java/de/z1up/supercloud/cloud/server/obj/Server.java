package de.z1up.supercloud.cloud.server.obj;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.interfaces.IServer;
import de.z1up.supercloud.core.mongo.MongoUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

public abstract class Server extends MongoUtils implements IServer {

    private final UID uid;
    private final int id;

    private ServerType serverType;
    private ServerMode serverMode;
    private String display;
    private Group group;
    private boolean maintenance;
    private String path;
    private boolean connected;
    private int port;
    private int maxPlayers;
    private String motd;

    private long pid;

    public Server(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path, boolean connected, int port, int maxPlayers, String motd) {
        this.uid = uid;
        this.serverType = serverType;
        this.serverMode = serverMode;
        this.display = display;
        this.group = group;
        this.maintenance = maintenance;
        this.id = id;
        this.path = path;
        this.connected = connected;
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.motd = motd;
    }

    public UID getUid() {
        return uid;
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

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public long getProcessPid() {
        return this.pid;
    }

    public void setProcessPid(long pid) {
        this.pid = pid;
    }

    @Override
    public void save() {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        Bson query = Filters
                .eq("uid.tag", this.uid.getTag());

        if(super.exists(collection, query)) {
            this.update0(collection);
        } else {

            final Document insert
                    = Document.parse(new Gson().toJson(this));

            super.insert(collection, insert);

        }

    }

    @Override
    public void update() {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        this.update0(collection);

    }

    private void update0(final MongoCollection<Document> collection) {

        final Bson query
                = Filters.eq("uid.tag", this.uid.getTag());

        final Document insert
                = Document.parse(new Gson().toJson(this));

        super.updateDocument(collection, query, insert);

    }
}
