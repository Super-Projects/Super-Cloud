package de.z1up.supercloud.cloud;

import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.GroupManager;
import de.z1up.supercloud.cloud.server.mngmt.ServerCreator;
import de.z1up.supercloud.cloud.server.mngmt.ServerManager;
import de.z1up.supercloud.cloud.server.obj.GameServer;
import de.z1up.supercloud.cloud.setup.SetupManager;
import de.z1up.supercloud.core.Utils;
import de.z1up.supercloud.core.chat.Logger;
import de.z1up.supercloud.core.file.CloudFile;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.mongo.MongoManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cloud {

    private static Cloud instance;

    private Logger          logger;
    private SetupManager    setupManager;
    private GroupManager groupManager;
    private ServerManager   serverManager;
    private ServerCreator   serverCreator;
    private MongoManager    mongoManager;

    public Cloud() {
        instance = this;
    }

    public void run() {

        this.init();
        this.load();

    }

    private synchronized void init() {
        this.logger           = new Logger();
        this.setupManager     = new SetupManager();
        this.groupManager     = new GroupManager();
        this.serverManager    = new ServerManager();
        this.serverCreator    = new ServerCreator();
        this.mongoManager     = new MongoManager();
    }

    synchronized void load() {

        // send the header to the console
        Utils.header();

        // check if cloud was shut down
        // gracefully
        if(!Utils.wasShutDownGracefully()) {
            Utils.warningNotShutdownGracefully();
        } else {
            try {
                Files.delete(Paths.get("logs//shutdown0.log"));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        // connect to the database
        if(!this.mongoManager.isConnected()) {
            this.mongoManager.connect();
        }

        // kill all the old servers which
        // weren't shut down gracefully
        this.getServerManager().killOldServers();

        GameServer server = new GameServer(
                new UID("1234we56", UIDType.SERVER),
                ServerType.SERVER,
                ServerMode.STATIC,
                "display-1",
                null,
                false,
                10,
                "path",
                false,
                12345,
                20,
                "Defintely not a motd");
        server.save();

        // load setup if necessary
        this.setupManager.loadSetUp();

    }


    @Deprecated
    public void shutdown() {

    }

    public void shutdownGracefully() {
        new CloudFile("logs//", "shutdown0.log");
    }

    public static Cloud getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public SetupManager getSetupManager() {
        return setupManager;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public ServerCreator getServerCreator() {
        return serverCreator;
    }

    public MongoManager getMongoManager() {
        return mongoManager;
    }
}
