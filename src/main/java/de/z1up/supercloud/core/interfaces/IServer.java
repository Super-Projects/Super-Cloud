package de.z1up.supercloud.core.interfaces;

import java.io.IOException;

public interface IServer ***REMOVED***

    void bootstrap() throws IOException;

    void shutdown();

    Process getProcess();

    Thread getThread();

    void save();

    void update();

***REMOVED***
