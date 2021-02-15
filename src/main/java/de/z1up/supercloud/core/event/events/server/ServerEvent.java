package de.z1up.supercloud.core.event.events.server;

import de.z1up.supercloud.cloud.server.Server;
import de.z1up.supercloud.core.event.handle.HandlerList;
import de.z1up.supercloud.core.event.handle.Event;

public abstract class ServerEvent implements Event {

    private final HandlerList handlers;
    private boolean cancelled;

    private Server server;

    public ServerEvent(Server server, boolean cancelled) {

        this.server = server;

        this.cancelled = cancelled;
        this.handlers = new HandlerList();
    }
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return this.handlers;
    }

    public Server getServer() {
        return server;
    }
}
