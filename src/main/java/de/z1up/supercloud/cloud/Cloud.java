package de.z1up.supercloud.cloud;
import de.z1up.supercloud.cloud.server.*;
import de.z1up.supercloud.cloud.server.mngmt.ServerCreator;
import de.z1up.supercloud.cloud.server.mngmt.ServerManager;
import de.z1up.supercloud.cloud.setup.SetupManager;
import de.z1up.supercloud.core.chat.Logger;

import java.io.IOException;

public class Cloud ***REMOVED***

    private static Cloud instance;

    private final Logger logger                 = new Logger();
    private final SetupManager setupManager     = new SetupManager();
    private final GroupManager groupManager     = new GroupManager();
    private final ServerManager serverManager   = new ServerManager();
    private final ServerCreator serverCreator   = new ServerCreator();

    public Cloud() ***REMOVED***
        instance = this;
    ***REMOVED***

    public void run() ***REMOVED***

        init();

        load();

        groupManager.loadGroups();

        Group lobbyGroup = groupManager.getGroupByName("Lobby");

        GameServer server = serverCreator.createServerByGroup(lobbyGroup);

        try ***REMOVED***
            serverManager.createServerEnvironment(server);
        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***

        try ***REMOVED***
            server.startProcess();
        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***


    ***REMOVED***

    void init() ***REMOVED***
        //logger = new Logger();
        //setupManager = new SetupManager();
    ***REMOVED***

    void load() ***REMOVED***

        setupManager.startSetUp();

    ***REMOVED***

    public static Cloud getInstance() ***REMOVED***
        return instance;
    ***REMOVED***


    public Logger getLogger() ***REMOVED***
        return logger;
    ***REMOVED***

    public SetupManager getSetupManager() ***REMOVED***
        return setupManager;
    ***REMOVED***

    public GroupManager getGroupManager() ***REMOVED***
        return groupManager;
    ***REMOVED***

    public ServerManager getServerManager() ***REMOVED***
        return serverManager;
    ***REMOVED***

    public ServerCreator getServerCreator() ***REMOVED***
        return serverCreator;
    ***REMOVED***
***REMOVED***
