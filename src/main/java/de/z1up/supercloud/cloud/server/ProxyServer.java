package de.z1up.supercloud.cloud.server;

import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.core.id.UID;

import java.io.IOException;

public class ProxyServer extends Server ***REMOVED***


    public ProxyServer(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path, int port, int maxPlayers, String motd) ***REMOVED***
        super(uid, serverType, serverMode, display, group, maintenance, id, path, port, maxPlayers, motd);
    ***REMOVED***

    @Override
    public void bootstrap() throws IOException ***REMOVED***

    ***REMOVED***

    @Override
    public void shutdown() ***REMOVED***

    ***REMOVED***

    @Override
    public Process getProcess() ***REMOVED***
        return null;
    ***REMOVED***

    @Override
    public Thread getThread() ***REMOVED***
        return null;
    ***REMOVED***
***REMOVED***
