package de.z1up.supercloud.core.event.handle;

import de.z1up.supercloud.core.event.handle.Event;
import de.z1up.supercloud.core.event.handle.EventHandler;
import de.z1up.supercloud.core.event.handle.HandleManager;
import de.z1up.supercloud.core.event.handle.Listener;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public class SimpleHandleManager implements HandleManager {

    @Override
    public Set<Method> detectAnnotations(Class<? super Listener> type) {
        return null;
    }

    /*
    @Override
    public Set<Method> detectAnnotations(Class<? extends Listener> type) {

        final Set<Method> methods
                = new HashSet<>();

        Class<?> clazz = type;

        while (clazz != Object.class) {

            for(final Method method : clazz.getDeclaredMethods()) {

                if(method.isAnnotationPresent(CloudEventHandler.class)) {

                    methods.add(method);
                }
            }

            clazz = clazz.getSuperclass();
        }

        return methods;
    }

     */

    @Override
    public Set<Method> detectAnnotations(Class<? extends Listener>... types) {

        final Set<Method> methods
                = new HashSet<>();

        for(Class<? extends Listener> type : types) {
            methods.addAll(detectAnnotations(type));
        }

        return methods;
    }

    @Override
    public <T> Set<Method> detectAnnotationsWithEvent(T targetEvent, Class<? extends Event>... type) {
        return null;
    }

    @Override
    public <T> Set<Method> detectAnnotationsWithEvent(T targetEvent, Class<? extends Event> type) {

        final Set<Method> methods
                = new HashSet<>();

        Class<?> clazz = type;

        while (clazz != Object.class) {

            for(final Method method : clazz.getDeclaredMethods()) {

                if(method.isAnnotationPresent(EventHandler.class)) {

                    methods.add(method);
                }
            }

            clazz = clazz.getSuperclass();
        }

        for(Method method : methods) {

            Object target = ((ParameterizedType) method
                    .getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            if(targetEvent.getClass().isInstance(target)) {

                methods.add(method);

            }

        }


        return methods;
    }
}
