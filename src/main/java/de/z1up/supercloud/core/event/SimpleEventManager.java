package de.z1up.supercloud.core.event;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.event.handle.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
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

            if(event.getHandlers() == null) {
                Cloud.getInstance().getLogger()
                        .log(excpt + "Event Handlers can't be null!");
                return;
            }

            if(event.getHandlers().isEmpty()) {
                Cloud.getInstance().getLogger()
                        .log(excpt + "No Event Handlers where found!");
                return;
            }

            this.fireEvent(event);

        } else {
            Cloud.getInstance().getLogger()
                    .debug(excpt + "Event was cancelled by system!");
        }

    }

    @Override
    public void fireEvent(final Event event) {

        if(event == null) {
            throw new NullPointerException("Event can't be null");
        }

        final String excpt = "Event "
                + event.getClass().getName() + " wasn't fired: ";

        final Set<Listener> eventListener
                = this.getRegisteredListeners(event.getClass());

        if(eventListener == null) {
            throw new NullPointerException(excpt + "Event Listeners can't be null!");
        }

        if(eventListener.isEmpty()) {
            throw new IllegalStateException(excpt + "Event Listeners can't be empty!");
        }

        eventListener.forEach(listener -> {

            final Set<Method> annotations
                    = this.getHandleManager().detectAnnotations(listener.getClass());

            for(Method annotation : annotations) {
                try {
                    annotation.invoke(annotation.getDeclaringClass().newInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }

        });

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
                Class<?> generic = (Class<?>) ((ParameterizedType) listener
                        .getClass().getGenericSuperclass()).getActualTypeArguments()[0];

                if (type.isInstance(generic)) {
                    handlers.add(listener);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Generic of wrong type! ");
            }

        }

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
