package de.z1up.supercloud.core.interfaces;

/**
 * The cancellable interface should be implemented when an
 * operation, such as starting a {@link Service}, can be stopped.
 *
 * It does not matter whether the process can be stopped by a
 * user or the system itself.
 *
 * @author      Christoph Langer
 * @version     1.1
 */
public interface Cancellable {

    /**
     * Resets the status of the operation. The process can be
     * stopped/interrupted or demonstrated using this method.
     *
     * @param   cancelled
     *          The new status of the operation, i.e. whether
     *          it has been stopped or not.
     */
    void setCancelled(boolean cancelled);

    /**
     * Returns the operations status of the operation. This can
     * be either in progress or interrupted ( true or false).
     *
     * @return  The boolean indicates whether the process is
     *          currently stopped or interrupted.
     */
    boolean isCancelled();

}
