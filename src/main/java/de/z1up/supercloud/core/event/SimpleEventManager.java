package de.z1up.supercloud.core.event;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.event.handle.*;
import de.z1up.supercloud.core.event.handle.Event;
import de.z1up.supercloud.core.event.listener.ListenerServerBootstrap;
import de.z1up.supercloud.core.event.server.ServerBootstrapEvent;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SimpleEventManager implements EventManager {

    private final Set<Listener> listeners;
    private final HandleManager handleManager;

    public SimpleEventManager() {

        this.listeners = new HashSet<>();
        this.handleManager = new SimpleHandleManager();

    }

    @Override
    public void callEvent(final Event event) {

        if(event == null) {
            Cloud.getInstance().getLogger()
                    .log("Event wasn't called: Event can't be null!");
            return;
        }

        final String excpt = "Event "
                + event.getClass().getName() + " wasn't called: ";

        if(!event.isCancelled()) {

            this.fireEvent(event);

        } else {
            Cloud.getInstance().getLogger()
                    .debug(excpt + "Event was cancelled by system!");
        }

    }

    @Override
    public void fireEvent(final Event event) {

        if (event == null) {
            System.out.println("Event cant be null!");
            return;
        }

        final String excpt = "Event "
                + event.getClass().getName() + " wasn't fired: ";

        final Set<Listener> eventListeners
                = this.getRegisteredListeners(event.getClass());

        eventListeners.forEach(
                listnr -> System.out.println(listnr.getClass().getName())
        );

        if (eventListeners == null) {
            System.out.println(excpt + "Event Listener cant be null!");
            return;
        }

        if (eventListeners.isEmpty()) {
            System.out.println(excpt + "Event Listener cant be empty!");
            return;
        }

        Iterator<Listener> it = listeners.iterator();

        while (it.hasNext()) {

            Listener listener = it.next();

            final Set<Method> annotations
                    = this.getHandleManager().detectAnnotations(listener.getClass());

            for (Method annotation : annotations) {

                try {
                    System.out.println("Annotion name ----->>>  " + annotation.getName());

                    annotation.setAccessible(true);
                    annotation
                            .invoke((ListenerServerBootstrap) listener,
                            (ServerBootstrapEvent) event);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public Set<Listener> getRegisteredListeners() {
        return this.listeners;
    }

    public Set<Listener> getRegisteredListeners(Class<? extends Event> type) {

        final Set<Listener> handlers
                = new HashSet<>();

        if(handlers == null) {
            throw new NullPointerException("Handler list can't be null!");
        }

        for(Listener listener : this.listeners) {

            try {

                Class<?> clazz = listener.getType();

                if(type.equals(clazz)) {
                    handlers.add(listener);
                    System.out.println("true : "  + clazz);
                } else {
                    System.out.println("false : " + clazz);
                }

            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Generic of wrong type! ");
            }

        }

        handlers.forEach(handler -> System.out.println(handler.getClass().getName()));

        return handlers;
    }

    public final HandleManager getHandleManager() {
        return this.handleManager;
    }

    @Override
    public void registerEvents(final Listener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void unregisterEvents(final Listener listener) {
        this.listeners.remove(listener);
    }
}
