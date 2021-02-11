package de.z1up.supercloud.core.time;

import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.interfaces.ICloudRunnable;

import java.util.concurrent.TimeUnit;

public abstract class CloudRunnable implements ICloudRunnable {

    private boolean active = false;
    private UID uid;

    @Override
    public void runTaskLater(TimeUnit unit, long delay) {

        uid = UID.randomUID(UIDType.RUNNABLE);

        this.setActive(true);

        try {
            unit.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        run();

        this.setActive(false);

    }

    @Override
    public void runTaskLaterAsync(final TimeUnit unit, final long delay) {

        uid = UID.randomUID(UIDType.RUNNABLE);

        this.setActive(true);

        new CloudThread() {
            @Override
            public void run() {

                try {
                    unit.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                run();

            }
        };

        this.setActive(false);

    }

    @Override
    public void runTaskTimer(TimeUnit unit, long delay, long period) {

        uid = UID.randomUID(UIDType.RUNNABLE);

        CloudRunnable.this.setActive(true);

        try {
            unit.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (CloudRunnable.this.active) {

            CloudRunnable.this.run();

            try {
                unit.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        CloudRunnable.this.setActive(false);

    }

    @Override
    public void runTaskTimerAsync(final TimeUnit unit, final long delay, final long period) {

        uid = UID.randomUID(UIDType.RUNNABLE);

        new CloudThread() {
            @Override
            public void run() {

                CloudRunnable.this.setActive(true);

                try {
                    unit.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (CloudRunnable.this.active) {

                    CloudRunnable.this.run();

                    try {
                        unit.sleep(period);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                CloudRunnable.this.setActive(false);

            }
        };

    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean val) {
        this.active = val;
    }

    @Override
    public UID getUniqueID() {
        return uid;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        
    }
}
