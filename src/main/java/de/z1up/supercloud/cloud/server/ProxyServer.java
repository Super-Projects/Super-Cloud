package de.z1up.supercloud.cloud.server;

import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.core.id.UID;

import java.io.IOException;

public class ProxyServer extends Server {

    public ProxyServer(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path) {
        super(uid, serverType, serverMode, display, group, maintenance, id, path);
    }

    @Override
    public void startProcess() throws IOException {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public Process getProcess() {
        return null;
    }

    @Override
    public Thread getThread() {
        return null;
    }
}
