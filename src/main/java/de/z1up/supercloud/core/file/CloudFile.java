package de.z1up.supercloud.core.file;

import de.z1up.supercloud.core.interfaces.DataFile;

import java.io.File;
import java.io.IOException;

public class CloudFile implements DataFile ***REMOVED***

    private String path;
    private String fileName;
    private File file;

    public CloudFile(CloudFolder folder, String fileName) ***REMOVED***
        this(folder.getPath(), fileName);
    ***REMOVED***

    public CloudFile(String path, String fileName) ***REMOVED***
        this.path = path;
        this.fileName = fileName;
        build();
    ***REMOVED***


    @Override
    public void build() ***REMOVED***

        File dir = new File(path);

        if(!dir.exists()) ***REMOVED***
            dir.mkdirs();
        ***REMOVED***

        file = new File(path, fileName);

        if(!file.exists()) ***REMOVED***
            try ***REMOVED***
                file.createNewFile();
            ***REMOVED*** catch (IOException exception) ***REMOVED***
                exception.printStackTrace();
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    @Override
    public String getPath() ***REMOVED***
        return path;
    ***REMOVED***

    @Override
    public File get() ***REMOVED***
        return file;
    ***REMOVED***

    @Override
    public boolean exists() ***REMOVED***
        return file.exists();
    ***REMOVED***

    public String getFileName() ***REMOVED***
        return fileName;
    ***REMOVED***
***REMOVED***
