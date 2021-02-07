package de.z1up.supercloud.cloud.server.obj;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.file.Copier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;

public class Template {

    private final String PATH = "local//templates";
    private String name;

    public Template(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void create() {

        File dir = new File(PATH + "//" + name);

        if(!dir.exists()) {
            dir.mkdirs();
        }

        File serverProperties = new File(PATH + "//" + name + "//server.properties");

        if(!serverProperties.exists()) {
            try {
                serverProperties.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        File pluginsFolder = new File(PATH + "//" + name + "//plugins");

        if(!pluginsFolder.exists()) {
            pluginsFolder.mkdirs();
        }

    }

    public Collection<File> getFiles() {

        Collection files = new ArrayList();

        File dir = new File(PATH + "//" + name);

        File[] contents = dir.listFiles();

        for (File content : contents) {

            if(content.exists()) {
                files.add(content);
            }

        }
        return files;
    }

    public Collection<File> getFiles(String path) {

        Collection files = new ArrayList();

        File dir = new File(path);

        File[] contents = dir.listFiles();

        for (File content : contents) {

            if(content.exists()) {
                files.add(content);
            }

        }
        return files;
    }

    /*
    public void copyTo(CloudFolder folder) {

        Collection<File> files = getFiles();
        Iterator<File> fileIterator = files.iterator();

        Path copyTo = Paths.get(folder.getPath());

        while (fileIterator.hasNext()) {

            File file = fileIterator.next();
            Path filePath = Paths.get(file.getPath());

            try {
                Files.copy(filePath, copyTo, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            if(file.isDirectory()) {

            }

        }
    }*/

    public void copyFromTo(File from, File destination) throws IOException {

        Cloud.getInstance().getLogger().debug("Copying template" + name + "...");

        File[] files  = from.listFiles();

        for(File file : files) {

            final String fileName = file.getName();
            System.out.println(fileName);
            Files.copy(Paths.get(file.getPath()), Paths.get(destination.getPath() + "//" + fileName), StandardCopyOption.REPLACE_EXISTING);

        }

    }
}