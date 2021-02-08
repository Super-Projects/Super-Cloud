package de.z1up.supercloud.core.event.handle;

public interface Listener<T extends Event> {

    Class<? extends T> getType();

    @EventHandler
    void onCall(T event);

}
