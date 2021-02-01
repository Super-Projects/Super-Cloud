package de.z1up.supercloud.core.time;

import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.interfaces.ICloudThread;

public abstract class CloudThread implements ICloudThread, Runnable {

    private Thread thread;
    private UID uid;

    @Override
    public void start() {

        uid = UID.randomUID(UIDType.THREAD);

        this.thread = new Thread(this);
        this.thread.start();

    }

    @Override
    public UID getUniqueID() {
        return uid;
    }
}
