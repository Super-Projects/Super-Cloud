package de.z1up.supercloud.core.interfaces;

import java.io.File;

public interface DataFile {

    void build();

    String getPath();

    File get();

    boolean exists();

}
