package de.z1up.supercloud.core.event.listener;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.Server;
import de.z1up.supercloud.core.event.handle.EventHandler;
import de.z1up.supercloud.core.event.handle.Listener;
import de.z1up.supercloud.core.event.events.server.ServerShutdownEvent;

public class ListenerServerShutdown implements Listener<ServerShutdownEvent> {

    public ListenerServerShutdown() {
        Cloud.getInstance().getEventManager().registerEvents(this);
    }

    @EventHandler
    public void onCall(final ServerShutdownEvent event) {

        final Server server
                = event.getServer();

        Cloud.getInstance().getLogger().log(server.getDisplay() + " at port " + server.getPort()
                + "just shut down! [" + server.getUniqueID().getTag() + "]");

    }

    @Override
    public Class getType() {
        return ServerShutdownEvent.class;
    }
}
