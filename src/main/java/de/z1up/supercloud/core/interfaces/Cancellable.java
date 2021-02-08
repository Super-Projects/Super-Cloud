package de.z1up.supercloud.core.interfaces;

public interface Cancellable {

    void setCancelled(boolean cancelled);

    boolean isCancelled();

}
