package de.z1up.supercloud.core.event.handle;

import de.z1up.supercloud.core.interfaces.Cancellable;

public interface Event extends Cancellable {

    HandlerList getHandlers();

}
