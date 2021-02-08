package de.z1up.supercloud.core.event.handle;

import java.util.concurrent.atomic.AtomicBoolean;

public class HandlerList {

    private Listener[] listeners;

    public HandlerList() {
        listeners = new Listener[0];
    }

    public Listener[] getListeners() {
        return listeners;
    }

    public void registerListener(final Listener listener) {
        final int length = listeners.length;
        listeners[length + 1] = listener;
    }

    public void unregisterListener(final Listener listener) {

        if(!this.isListenerRegistered(listener)) {
            return;
        }

        int pos = this.getPos(listener);
        this.listeners = this.removeItem(pos);

    }

    private synchronized Listener[] removeItem(int pos) {

        final int length = this.listeners.length;
        Listener[] nl = listeners.clone();

        for(int i = pos; i < length - 1; i++) {

            final Listener above = this.listeners[i + 1];
            nl[pos] = above;

        }

        return nl;
    }

    private int getPos(final Listener listener) {

        int p = 0;

        for (Listener val : listeners) {
            if(val.equals(listener)) {
                break;
            }
            p++;
        }

        return p;
    }

    public boolean isListenerRegistered(final Listener listener) {

        AtomicBoolean registered = new AtomicBoolean(false);

        for (Listener val : listeners) {
            if(val.equals(listener))
                registered.set(true);
        }

        return registered.get();
    }

    public boolean isEmpty() {
        return (this.listeners.length > 0 ? false : true);
    }

}
