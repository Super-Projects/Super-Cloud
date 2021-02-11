package de.z1up.supercloud.core.time;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.interfaces.ICloudThread;

public abstract class CloudThread extends Thread implements ICloudThread {

    private UID         uid;
    private boolean     cancelled;

    public CloudThread() {
        CloudThread.this.start();
    }

    @Override
    public void start() {

        if(this.isCancelled()) {
           return;
        }

        this.uid = UID.randomUID(UIDType.THREAD);
        super.start();
    }

    @Override
    public UID getUniqueID() {
        return this.uid;
    }

    @Override
    public void shutdown() {

        if((this.isAlive()) && (!this.isCancelled())) {

            try {

                super.sleep(2000);
                super.stop();

            } catch (InterruptedException exc) {

                super.interrupt();
                Cloud.getInstance().getLogger().debug(exc.getMessage());

            }

        }

    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
