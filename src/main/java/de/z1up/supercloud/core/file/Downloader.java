package de.z1up.supercloud.core.file;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.time.CloudThread;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Downloader ***REMOVED***

    private URL url;
    private String path;
    private String fileName;

    public Downloader(String urlPath, String path, String fileName) ***REMOVED***

        try ***REMOVED***
            this.url = new URL(urlPath);
        ***REMOVED*** catch (MalformedURLException e) ***REMOVED***
            e.printStackTrace();
        ***REMOVED***

        this.path = path;
        this.fileName = fileName;

    ***REMOVED***

    public void downloadAsync() ***REMOVED***
        new CloudThread() ***REMOVED***
            @Override
            public void run() ***REMOVED***
                download();
            ***REMOVED***
        ***REMOVED***.start();
    ***REMOVED***

    private void download() ***REMOVED***

        try ***REMOVED***

            File dir = new File(path);

            if(!dir.exists())***REMOVED***
                dir.mkdirs();
            ***REMOVED***

            File file = new File(path, fileName);

            if(!file.exists()) ***REMOVED***
                file.createNewFile();
            ***REMOVED***

            InputStream in = url.openStream();
            Files.copy(in, Paths.get(path + "//" + fileName), StandardCopyOption.REPLACE_EXISTING);

            Cloud.getInstance().getLogger().log("Download of " + fileName + " finished!");

        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***

    ***REMOVED***

***REMOVED***
