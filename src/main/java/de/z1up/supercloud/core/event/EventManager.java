package de.z1up.supercloud.core.event;

import de.z1up.supercloud.core.event.handle.Event;
import de.z1up.supercloud.core.event.handle.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public interface EventManager {

    void callEvent(Event event);

    void fireEvent(Event event);

    Set<Listener> getRegisteredListeners();

    void registerEvents(Listener listener);

    void unregisterEvents(Listener listener);

}
