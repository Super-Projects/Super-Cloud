package de.z1up.supercloud.core.event.listener;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.obj.Server;
import de.z1up.supercloud.core.event.handle.EventHandler;
import de.z1up.supercloud.core.event.handle.Listener;
import de.z1up.supercloud.core.event.server.ServerShutdownEvent;

public class ListenerServerShutdown implements Listener {

    public ListenerServerShutdown() {
        Cloud.getInstance().getEventManager().registerEvents(this);
    }

    @EventHandler
    public void onCall(final ServerShutdownEvent event) {

        Server server = event.getServer();
        System.out.println("server " + server.getDisplay() + " on port " + server.getPort() + " just shut down!");

    }

}
