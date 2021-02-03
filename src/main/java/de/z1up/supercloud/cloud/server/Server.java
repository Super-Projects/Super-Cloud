package de.z1up.supercloud.cloud.server;

import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.interfaces.IServer;

public abstract class Server implements IServer ***REMOVED***

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

    public Server(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path, int port, int maxPlayers, String motd) ***REMOVED***
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
    ***REMOVED***

    public UID getUid() ***REMOVED***
        return uid;
    ***REMOVED***

    public void setUid(UID uid) ***REMOVED***
        this.uid = uid;
    ***REMOVED***

    public ServerType getServerType() ***REMOVED***
        return serverType;
    ***REMOVED***

    public void setServerType(ServerType serverType) ***REMOVED***
        this.serverType = serverType;
    ***REMOVED***

    public ServerMode getServerMode() ***REMOVED***
        return serverMode;
    ***REMOVED***

    public void setServerMode(ServerMode serverMode) ***REMOVED***
        this.serverMode = serverMode;
    ***REMOVED***

    public String getDisplay() ***REMOVED***
        return display;
    ***REMOVED***

    public void setDisplay(String display) ***REMOVED***
        this.display = display;
    ***REMOVED***

    public Group getGroup() ***REMOVED***
        return group;
    ***REMOVED***

    public void setGroup(Group group) ***REMOVED***
        this.group = group;
    ***REMOVED***

    public boolean isMaintenance() ***REMOVED***
        return maintenance;
    ***REMOVED***

    public void setMaintenance(boolean maintenance) ***REMOVED***
        this.maintenance = maintenance;
    ***REMOVED***

    public void setPath(String path) ***REMOVED***
        this.path = path;
    ***REMOVED***

    public String getPath() ***REMOVED***
        return path;
    ***REMOVED***

    public int getId() ***REMOVED***
        return id;
    ***REMOVED***

    public void setId(int id) ***REMOVED***
        this.id = id;
    ***REMOVED***

    public int getPort() ***REMOVED***
        return port;
    ***REMOVED***

    public void setPort(int port) ***REMOVED***
        this.port = port;
    ***REMOVED***

    public int getMaxPlayers() ***REMOVED***
        return maxPlayers;
    ***REMOVED***

    public void setMaxPlayers(int maxPlayers) ***REMOVED***
        this.maxPlayers = maxPlayers;
    ***REMOVED***

    public String getMotd() ***REMOVED***
        return motd;
    ***REMOVED***

    public void setMotd(String motd) ***REMOVED***
        this.motd = motd;
    ***REMOVED***

***REMOVED***
