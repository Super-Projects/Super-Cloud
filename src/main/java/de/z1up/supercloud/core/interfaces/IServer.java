package de.z1up.supercloud.core.interfaces;

import java.io.IOException;

public interface IServer {

    void bootstrap() throws IOException;

    void shutdown();

    Process getProcess();

    Thread getThread();

    void save();

    void update();

}
