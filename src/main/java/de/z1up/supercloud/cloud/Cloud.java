package de.z1up.supercloud.cloud;
import de.z1up.supercloud.cloud.server.*;
import de.z1up.supercloud.cloud.server.mngmt.ServerCreator;
import de.z1up.supercloud.cloud.server.mngmt.ServerManager;
import de.z1up.supercloud.cloud.setup.SetupManager;
import de.z1up.supercloud.core.chat.Logger;

import java.io.IOException;

public class Cloud {

    private static Cloud instance;

    private final Logger logger                 = new Logger();
    private final SetupManager setupManager     = new SetupManager();
    private final GroupManager groupManager     = new GroupManager();
    private final ServerManager serverManager   = new ServerManager();
    private final ServerCreator serverCreator   = new ServerCreator();

    public Cloud() {
        instance = this;
    }

    public void run() {

        init();

        load();

        groupManager.loadGroups();

        Group lobbyGroup = groupManager.getGroupByName("Lobby");

        GameServer server = serverCreator.createServerByGroup(lobbyGroup);

        try {
            serverManager.createServerEnvironment(server);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            server.startProcess();
        } catch (IOException exception) {
            exception.printStackTrace();
        }


    }

    void init() {
        //logger = new Logger();
        //setupManager = new SetupManager();
    }

    void load() {

        setupManager.startSetUp();

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
}
