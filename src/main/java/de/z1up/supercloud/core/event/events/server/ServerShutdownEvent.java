package de.z1up.supercloud.core.event.events.server;

import de.z1up.supercloud.cloud.server.Server;

public class ServerShutdownEvent extends ServerEvent {

    public ServerShutdownEvent(Server server, boolean cancelled) {
        super(server, cancelled);
    }
}
