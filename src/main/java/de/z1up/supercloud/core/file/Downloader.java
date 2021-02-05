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

public class Downloader {

    private URL url;
    private String path;
    private String fileName;

    public Downloader(String urlPath, String path, String fileName) {

        try {
            this.url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.path = path;
        this.fileName = fileName;

    }

    public void downloadAsync() {
        new CloudThread() {
            @Override
            public void run() {
                download();
            }
        }.start();
    }

    private void download() {

        try {

            File dir = new File(path);

            if(!dir.exists()){
                dir.mkdirs();
            }

            File file = new File(path, fileName);

            if(!file.exists()) {
                file.createNewFile();
            }

            InputStream in = url.openStream();
            Files.copy(in, Paths.get(path + "//" + fileName), StandardCopyOption.REPLACE_EXISTING);

            Cloud.getInstance().getLogger().log("Download of " + fileName + " finished!");

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}
