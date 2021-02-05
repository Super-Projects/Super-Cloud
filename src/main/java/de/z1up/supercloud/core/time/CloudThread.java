package de.z1up.supercloud.core.time;

import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.interfaces.ICloudThread;

public abstract class CloudThread implements ICloudThread, Runnable ***REMOVED***

    private Thread thread;
    private UID uid;

    @Override
    public void start() ***REMOVED***

        uid = UID.randomUID(UIDType.THREAD);

        this.thread = new Thread(this);
        this.thread.start();

    ***REMOVED***

    @Override
    public UID getUniqueID() ***REMOVED***
        return uid;
    ***REMOVED***
***REMOVED***
