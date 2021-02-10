package de.z1up.supercloud.cloud;

import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.cloud.server.group.GroupManager;
import de.z1up.supercloud.cloud.server.mngmt.ServerCreator;
import de.z1up.supercloud.cloud.server.mngmt.ServerManager;
import de.z1up.supercloud.cloud.server.obj.GameServer;
import de.z1up.supercloud.cloud.setup.SetupManager;
import de.z1up.supercloud.core.Utils;
import de.z1up.supercloud.core.chat.Logger;
import de.z1up.supercloud.core.event.EventManager;
import de.z1up.supercloud.core.event.SimpleEventManager;
import de.z1up.supercloud.core.event.listener.ListenerServerBootstrap;
import de.z1up.supercloud.core.event.listener.ListenerServerShutdown;
import de.z1up.supercloud.core.file.CloudFile;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.input.CommandLine;
import de.z1up.supercloud.core.input.InputReader;
import de.z1up.supercloud.core.mongo.MongoManager;
import de.z1up.supercloud.core.thread.ThreadManager;
import de.z1up.supercloud.core.time.CloudRunnable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Cloud {

    private static Cloud        instance;

    private Logger              logger;
    private SetupManager        setupManager;
    private GroupManager        groupManager;
    private ServerManager       serverManager;
    private ServerCreator       serverCreator;
    private MongoManager        mongoManager;
    private ThreadManager       threadManager;
    private EventManager        eventManager;
    private CommandLine         commandLine;

    public Cloud() {
        instance = this;
    }

    public void run() {

        this.init();
        this.load();

    }

    private synchronized void init() {
        this.logger             = new Logger();
        this.eventManager       = new SimpleEventManager();
        this.setupManager       = new SetupManager();
        this.groupManager       = new GroupManager();
        this.serverManager      = new ServerManager();
        this.serverCreator      = new ServerCreator();
        this.mongoManager       = new MongoManager();
        this.threadManager      = new ThreadManager();
        this.commandLine        = new CommandLine();
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
                if(Files.exists(Paths.get("logs//shutdown0.log"))) {
                    Files.delete(Paths.get("logs//shutdown0.log"));
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        // register the event listeners
        this.registerListeners();

        // connect to the database
        if(!this.mongoManager.isConnected()) {
            this.mongoManager.connect();
        }

        // kill all the old servers which
        // weren't shut down gracefully
        this.getServerManager().killOldServers();

        // load setup if necessary
        this.setupManager.loadSetUp();

        // open the command line
        this.commandLine.open();
        this.commandLine.read();

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

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    private final synchronized void registerListeners() {

        new ListenerServerBootstrap();
        new ListenerServerShutdown();

    }
}
