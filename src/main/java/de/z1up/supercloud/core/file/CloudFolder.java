package de.z1up.supercloud.core.file;

import de.z1up.supercloud.core.interfaces.DataFile;

import java.io.File;

public class CloudFolder implements DataFile {

    private String path;
    private File dir;

    public CloudFolder(String path) {
        this.path = path;
        build();
    }

    @Override
    public void build() {

        dir = new File(path);

        if(!dir.exists()) {
            dir.mkdirs();
        }

    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public File get() {
        return dir;
    }

    @Override
    public boolean exists() {
        return dir.exists();
    }
}
