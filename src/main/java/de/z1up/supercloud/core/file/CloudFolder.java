package de.z1up.supercloud.core.file;

import de.z1up.supercloud.core.interfaces.DataFile;

import java.io.File;

public class CloudFolder implements DataFile ***REMOVED***

    private String path;
    private File dir;

    public CloudFolder(String path) ***REMOVED***
        this.path = path;
        build();
    ***REMOVED***

    @Override
    public void build() ***REMOVED***

        dir = new File(path);

        if(!dir.exists()) ***REMOVED***
            dir.mkdirs();
        ***REMOVED***

    ***REMOVED***

    @Override
    public String getPath() ***REMOVED***
        return path;
    ***REMOVED***

    @Override
    public File get() ***REMOVED***
        return dir;
    ***REMOVED***

    @Override
    public boolean exists() ***REMOVED***
        return dir.exists();
    ***REMOVED***
***REMOVED***
