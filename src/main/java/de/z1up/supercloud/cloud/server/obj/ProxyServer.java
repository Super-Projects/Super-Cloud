package de.z1up.supercloud.cloud.server.obj;

import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.time.CloudThread;

import java.io.IOException;

public class ProxyServer extends Server {

    public ProxyServer(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path, boolean connected, int port, int maxPlayers, String motd) {
        super(uid, serverType, serverMode, display, group, maintenance, id, path, connected, port, maxPlayers, motd);
    }

    @Override
    public void bootstrap() throws IOException {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public Process getProcess() {
        return null;
    }

    @Override
    public CloudThread getThread() {
        return null;
    }

    @Override
    public void createEnvironment() throws IOException {

    }
}
