package de.z1up.supercloud.cloud;

import de.z1up.supercloud.cloud.server.group.GroupManager;
import de.z1up.supercloud.cloud.server.mngmt.ServerCreator;
import de.z1up.supercloud.cloud.server.mngmt.ServerManager;
import de.z1up.supercloud.cloud.setup.SetupManager;
import de.z1up.supercloud.core.Core;
import de.z1up.supercloud.core.Utils;
import de.z1up.supercloud.core.chat.Logger;
import de.z1up.supercloud.core.event.EventManager;
import de.z1up.supercloud.core.event.SimpleEventManager;
import de.z1up.supercloud.core.event.listener.ListenerServerBootstrap;
import de.z1up.supercloud.core.event.listener.ListenerServerShutdown;
import de.z1up.supercloud.core.file.CloudFile;
import de.z1up.supercloud.core.input.CommandLine;
import de.z1up.supercloud.core.interfaces.Sender;
import de.z1up.supercloud.core.mongo.MongoManager;
import de.z1up.supercloud.core.thread.ThreadManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Cloud implements Sender {

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

        // add a shutdown hook so that
        // files and others are saved correctly
        this.addShutdownHook();

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

        // load the settings manager
        Core.getInstance().loadSettingsManager();

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
        return this.logger;
    }

    public SetupManager getSetupManager() {
        return this.setupManager;
    }

    public GroupManager getGroupManager() {
        return this.groupManager;
    }

    public ServerManager getServerManager() {
        return this.serverManager;
    }

    public ServerCreator getServerCreator() {
        return this.serverCreator;
    }

    public MongoManager getMongoManager() {
        return this.mongoManager;
    }

    public ThreadManager getThreadManager() {
        return this.threadManager;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public CommandLine getCommandLine() {
        return this.commandLine;
    }

    private synchronized void registerListeners() {

        new ListenerServerBootstrap();
        new ListenerServerShutdown();

    }

    private void addShutdownHook() {

        Thread hook = new Thread(() -> {
            this.shutdownGracefully();
        });
        Runtime.getRuntime().addShutdownHook(hook);

    }

    @Override
    public String getName() {
        return "Cloud";
    }

    @Override
    public Class getDeclaringClass() {
        return this.getClass();
    }

    @Override
    public String getDisplay() {
        return "§8[§bSuperCloud§8]";
    }
}
