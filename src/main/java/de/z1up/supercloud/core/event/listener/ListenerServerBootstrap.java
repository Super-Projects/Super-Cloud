package de.z1up.supercloud.core.event.listener;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.obj.Server;
import de.z1up.supercloud.core.event.handle.EventHandler;
import de.z1up.supercloud.core.event.handle.Listener;
import de.z1up.supercloud.core.event.server.ServerBootstrapEvent;

public class ListenerServerBootstrap implements Listener<ServerBootstrapEvent> {

    public ListenerServerBootstrap() {
        Cloud.getInstance().getEventManager().registerEvents(this);
    }

    @EventHandler
    public void onCall(final ServerBootstrapEvent event) {

        final Server server
                = event.getServer();

        Cloud.getInstance().getLogger().log(server.getDisplay() + " just bootstrapped at port " + server.getPort()
                + "! [" + server.getUniqueID().getTag() + "]");

    }

    @Override
    public Class getType() {
        return ServerBootstrapEvent.class;
    }
}
