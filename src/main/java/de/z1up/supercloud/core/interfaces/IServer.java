package de.z1up.supercloud.core.interfaces;

import java.io.IOException;

public interface IServer {

    void startProcess() throws IOException;

    void shutdown();

    Process getProcess();

    Thread getThread();

}
