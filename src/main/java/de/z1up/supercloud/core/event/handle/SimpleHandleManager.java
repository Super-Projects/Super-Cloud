package de.z1up.supercloud.core.event.handle;

import de.z1up.supercloud.core.event.listener.ListenerServerBootstrap;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public class SimpleHandleManager implements HandleManager {

    @Override
    public Set<Method> detectAnnotations(Class<? extends Listener> clazz) {
        final Set<Method> methods
                = new HashSet<>();


        for(Method method : clazz.getDeclaredMethods()) {

            if(method.isAnnotationPresent(EventHandler.class)) {

                methods.add(method);
                break;

            }

        }


        return methods;
    }

    @Override
    public Set<Method> detectAnnotations(Class<? extends Listener>... types) {

        final Set<Method> methods
                = new HashSet<>();

        for(Class<? extends Listener> type : types) {

            Set<Method> m = detectAnnotations(type);
            methods.addAll(m);

        }

        return methods;
    }

    @Override
    public <T> Set<Method> detectAnnotationsWithEvent(T targetEvent, Class<? extends Listener>... listeners) {


        final Set<Method> methods
                = new HashSet<>();

        for(Class<? extends Listener> listener : listeners) {
            methods.addAll(detectAnnotationsWithEvent(targetEvent, listener));
        }

        return methods;


    }

    @Override
    public <T> Set<Method> detectAnnotationsWithEvent(T targetEvent, Class<? extends Listener> type) {

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
