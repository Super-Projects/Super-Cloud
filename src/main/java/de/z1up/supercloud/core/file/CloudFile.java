package de.z1up.supercloud.core.file;

import de.z1up.supercloud.core.interfaces.DataFile;

import java.io.File;
import java.io.IOException;

public class CloudFile implements DataFile {

    private String path;
    private String fileName;
    private File file;

    public CloudFile(CloudFolder folder, String fileName) {
        this(folder.getPath(), fileName);
    }

    public CloudFile(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
        build();
    }


    @Override
    public void build() {

        File dir = new File(path);

        if(!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(path, fileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public File get() {
        return file;
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    public String getFileName() {
        return fileName;
    }
}
