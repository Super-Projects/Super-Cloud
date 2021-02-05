package de.z1up.supercloud.core.interfaces;

import de.z1up.supercloud.core.id.UID;

public interface ICloudThread {

    void run();

    void start();

    UID getUniqueID();

}
