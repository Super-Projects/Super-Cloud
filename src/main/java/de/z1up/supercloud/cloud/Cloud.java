package de.z1up.supercloud.cloud;

import de.z1up.supercloud.cloud.server.*;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.mngmt.ServerCreator;
import de.z1up.supercloud.cloud.server.mngmt.ServerManager;
import de.z1up.supercloud.cloud.setup.SetupManager;
import de.z1up.supercloud.core.chat.Logger;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.mongo.MongoManager;

public class Cloud ***REMOVED***

    private static Cloud instance;

    private Logger          logger;
    private SetupManager    setupManager;
    private GroupManager    groupManager;
    private ServerManager   serverManager;
    private ServerCreator   serverCreator;
    private MongoManager    mongoManager;

    public Cloud() ***REMOVED***
        instance = this;
    ***REMOVED***

    public void run() ***REMOVED***

        this.init();
        this.load();

    ***REMOVED***

    private synchronized void init() ***REMOVED***
        this.logger           = new Logger();
        this.setupManager     = new SetupManager();
        this.groupManager     = new GroupManager();
        this.serverManager    = new ServerManager();
        this.serverCreator    = new ServerCreator();
        this.mongoManager     = new MongoManager();
    ***REMOVED***

    void load() ***REMOVED***

        // connect to the database
        if(!this.mongoManager.isConnected()) ***REMOVED***
            this.mongoManager.connect();
        ***REMOVED***

        GameServer server = new GameServer(new UID("1234we56", UIDType.SERVER),
                ServerType.SERVER,
                ServerMode.STATIC,
                "display-1",
                null,
                false,
                10,
                "path",
                12345,
                20,
                "a motd");
        server.save();

        // load setup if necessary
        this.setupManager.loadSetUp();

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

    public MongoManager getMongoManager() ***REMOVED***
        return mongoManager;
    ***REMOVED***
***REMOVED***
