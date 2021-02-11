package de.z1up.supercloud.core.interfaces;

import de.z1up.supercloud.core.id.UID;

/**
 * An ICloudThread is an interface that should be passed
 * to all threads created by the cloud. The easiest way
 * to use this ICloudThread is to create a new object of
 * the {@link de.z1up.supercloud.core.time.CloudThread}
 * class. This implements the ICloudThread interface.
 *
 * @author  Christoph Langer
 * @since   1.0
 *
 * @see     java.lang.Runnable
 * @see     de.z1up.supercloud.core.time.CloudThread
 * @see     de.z1up.supercloud.core.interfaces.CloudObject
 * @see     de.z1up.supercloud.core.interfaces.Cancellable
 */
public interface ICloudThread extends Runnable, CloudObject, Cancellable {

    /**
     * Stop the thread. All processes are finally aborted and stopped.
     * Should only be called if it is certain that data created in the
     * thread itself is also saved.
     */
    void shutdown();

    /**
     * Since all CloudObjects should contain a UID to uniquely
     * identify them, the ICloudThread must also contain one.
     * Via the {@code getUniqueID()} method, this can be accessed.
     *
     * @return  The {@link UID} of the ICloudThread.
     */
    UID getUniqueID();

}
