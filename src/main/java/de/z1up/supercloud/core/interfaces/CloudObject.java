package de.z1up.supercloud.core.interfaces;

import de.z1up.supercloud.core.id.UID;

/**
 * This interface must be passed to all CloudObjects. Cloud
 * objects are objects created by the cloud and contained in
 * the source code.
 *
 * In order to be able to identify them uniquely, they need a
 * {@link UID}. Objects can be filtered via this.
 *
 * @author  Christoph Langer
 * @since   1.2
 * @see     UID
 */
public interface CloudObject {

    /**
     * Since every CloudObject needs to contain a UID, it can be
     * accessed via the {@code getUniqueID()} method.
     *
     * @return The UID of the CloudObject
     */
    UID getUniqueID();

}
