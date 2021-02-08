package de.z1up.supercloud.core.event.handle;

import java.lang.reflect.Method;
import java.util.Set;

public interface HandleManager {

    Set<Method> detectAnnotations(Class<? extends Listener> type);

    Set<Method> detectAnnotations(Class<? extends Listener>... type);

    <T> Set<Method> detectAnnotationsWithEvent(T targetEvent, Class<? extends Listener> type);

    <T> Set<Method> detectAnnotationsWithEvent(T targetEvent, Class<? extends Listener>... type);

}
