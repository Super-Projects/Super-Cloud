package de.z1up.supercloud.core.interfaces;

import de.z1up.supercloud.core.time.CloudThread;

import java.io.IOException;

public interface Service extends CloudObject, Cancellable {

    void bootstrap() throws IOException;

    void shutdown();

    Process getProcess();

    CloudThread getThread();

    void save();

    void update();

    void createEnvironment() throws IOException;

}
