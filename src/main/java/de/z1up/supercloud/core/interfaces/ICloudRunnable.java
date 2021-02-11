package de.z1up.supercloud.core.interfaces;

import de.z1up.supercloud.core.id.UID;

import java.util.concurrent.TimeUnit;

public interface ICloudRunnable extends Runnable, CloudObject, Cancellable {

    void runTaskLater(TimeUnit unit, long delay);

    void runTaskLaterAsync(TimeUnit unit, long delay);

    void runTaskTimer(TimeUnit unit, long delay, long period);

    void runTaskTimerAsync(TimeUnit unit, long delay, long period);

    boolean isActive();

    void setActive(boolean val);

}
