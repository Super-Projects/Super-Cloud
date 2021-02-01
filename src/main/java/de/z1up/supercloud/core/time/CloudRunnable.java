package de.z1up.supercloud.core.time;

import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.interfaces.ICloudRunnable;

import java.util.concurrent.TimeUnit;

public abstract class CloudRunnable implements ICloudRunnable ***REMOVED***

    private boolean active = false;
    private UID uid;

    @Override
    public void runTaskLater(TimeUnit unit, long delay) ***REMOVED***

        uid = UID.randomUID(UIDType.RUNNABLE);

        this.setActive(true);

        try ***REMOVED***
            unit.sleep(delay);
        ***REMOVED*** catch (InterruptedException e) ***REMOVED***
            e.printStackTrace();
        ***REMOVED***

        run();

        this.setActive(false);

    ***REMOVED***

    @Override
    public void runTaskLaterAsync(final TimeUnit unit, final long delay) ***REMOVED***

        uid = UID.randomUID(UIDType.RUNNABLE);

        this.setActive(true);

        new CloudThread() ***REMOVED***
            @Override
            public void run() ***REMOVED***

                try ***REMOVED***
                    unit.sleep(delay);
                ***REMOVED*** catch (InterruptedException e) ***REMOVED***
                    e.printStackTrace();
                ***REMOVED***

                run();

            ***REMOVED***
        ***REMOVED***.start();

        this.setActive(false);

    ***REMOVED***

    @Override
    public void runTaskTimer(TimeUnit unit, long delay, long period) ***REMOVED***

        uid = UID.randomUID(UIDType.RUNNABLE);

        CloudRunnable.this.setActive(true);

        try ***REMOVED***
            unit.sleep(delay);
        ***REMOVED*** catch (InterruptedException e) ***REMOVED***
            e.printStackTrace();
        ***REMOVED***

        while (CloudRunnable.this.active) ***REMOVED***

            CloudRunnable.this.run();

            try ***REMOVED***
                unit.sleep(period);
            ***REMOVED*** catch (InterruptedException e) ***REMOVED***
                e.printStackTrace();
            ***REMOVED***

        ***REMOVED***

        CloudRunnable.this.setActive(false);

    ***REMOVED***

    @Override
    public void runTaskTimerAsync(final TimeUnit unit, final long delay, final long period) ***REMOVED***

        uid = UID.randomUID(UIDType.RUNNABLE);

        new CloudThread() ***REMOVED***
            @Override
            public void run() ***REMOVED***

                CloudRunnable.this.setActive(true);

                try ***REMOVED***
                    unit.sleep(delay);
                ***REMOVED*** catch (InterruptedException e) ***REMOVED***
                    e.printStackTrace();
                ***REMOVED***

                while (CloudRunnable.this.active) ***REMOVED***

                    CloudRunnable.this.run();

                    try ***REMOVED***
                        unit.sleep(period);
                    ***REMOVED*** catch (InterruptedException e) ***REMOVED***
                        e.printStackTrace();
                    ***REMOVED***

                ***REMOVED***

                CloudRunnable.this.setActive(false);

            ***REMOVED***
        ***REMOVED***.start();

    ***REMOVED***

    @Override
    public boolean isActive() ***REMOVED***
        return this.active;
    ***REMOVED***

    @Override
    public void setActive(boolean val) ***REMOVED***
        this.active = val;
    ***REMOVED***

    @Override
    public UID getUniqueID() ***REMOVED***
        return uid;
    ***REMOVED***
***REMOVED***
