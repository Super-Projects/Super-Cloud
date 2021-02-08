package de.z1up.supercloud.cloud.server.obj;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.core.Utils;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.interfaces.IServer;
import de.z1up.supercloud.core.mongo.MongoUtils;
import de.z1up.supercloud.core.screen.Screen;
import de.z1up.supercloud.core.time.CloudThread;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private transient CloudThread thread;
    private transient Process process;
    private transient Screen screen;

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

    public long getPid() {
        return this.pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Override
    public CloudThread getThread() {
        return thread;
    }

    @Override
    public Process getProcess() {
        return process;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void setThread(CloudThread thread) {
        this.thread = thread;
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

        Bson query = Filters
                .eq("uid.tag", this.uid.getTag());

        if(super.exists(collection, query)) {
            this.update0(collection);
        } else {
            this.save();
        }

    }

    private void update0(final MongoCollection<Document> collection) {

        final Bson query
                = Filters.eq("uid.tag", this.uid.getTag());


        final Document insert
                = Document.parse(new Gson().toJson(this));

        super.updateDocument(collection, query, insert);

    }

    @Override
    public void shutdown() {

        // check if thread uid even exists
        if(this.thread == null) {
            Cloud.getInstance().getLogger()
                    .debug("no thread found for server " + this.getUid().getTag());
            return;
        }

        final String threadTag
                = this.getThread().getUniqueID().getTag();

        // close the screen
        if(this.screen == null) {
            Cloud.getInstance().getLogger()
                    .debug("no screen found for " + this.getDisplay());
        } else {
            this.screen.close();
        }


        // destroy the server process

        if(this.process != null) {

            if(this.process.isAlive()) {

                try {

                    this.process.wait(1000);
                    this.process.destroy();

                } catch (InterruptedException exc) {

                    Cloud.getInstance().getLogger()
                            .debug(exc.getMessage());
                    this.shutdownForcibly();

                }

            } else {
                Cloud.getInstance().getLogger()
                        .debug("process in thread " + threadTag + " isn't alive");
            }

        } else {
            Cloud.getInstance().getLogger()
                    .debug("process in thread " + threadTag + " doesn't exists");
        }


        // finally shut the thread down
        if(this.getThread().isAlive()) {

            this.getThread().shutdown();

        } else {
            Cloud.getInstance().getLogger()
                    .debug("thread " + threadTag + " isn't alive");
        }

        this.setConnected(false);

        final MongoCollection<Document> collection
                = Cloud.getInstance().getMongoManager().getDatabase().getCollection("servers");

        try {
            this.destroy0(collection);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void shutdownForcibly() {

        if((this.process != null)
                && (this.process.isAlive())) {
            this.process.destroyForcibly();
        }

        if((this.thread != null)
                && (this.thread.isAlive())) {
            this.thread.shutdown();
        }

    }


    public void destroy() {

        final MongoCollection<Document> collection
                = Cloud.getInstance().getMongoManager().getDatabase().getCollection("servers");

        try {
            this.destroy0(collection);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void destroy0(final MongoCollection<Document> collection) throws IOException {

        final Document query = new Document();
        query.append("uid.tag", this.uid.getTag());

        if(thread != null) {
            if(this.thread.isAlive()) {
                this.shutdown();
            }
        }

        if(this.process == null) {

            ProcessHandle handle = Utils.getProcess(this.pid);

            if(handle != null) {

                if(handle.isAlive()) {
                    handle.destroyForcibly();
                }

            }

        }

        System.out.println("Waiting for dfeleting");
        if(Files.exists(Path.of(this.getPath()))) {
            System.out.println("deleting: " + this.getPath());
            Files.delete(Path.of(this.getPath()));
        }

        if(super.exists(collection, query)) {

            final DeleteResult result = super.delete(collection, query);
            Cloud.getInstance().getLogger().debug("Deleted " + result.getDeletedCount() + " servers from collection 'servers'");

        }

    }

}
