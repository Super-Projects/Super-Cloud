package de.z1up.supercloud.core.event.events.server;

import de.z1up.supercloud.cloud.server.Server;

public class ServerBootstrapEvent extends ServerEvent {

    private Result result;

    public ServerBootstrapEvent(final Server server, Result result, boolean cancelled) {
        super(server, cancelled);
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public enum Result {
        FAILURE, SUCCESS
    }
}
