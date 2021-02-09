package de.z1up.supercloud.cloud.server.obj;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.file.Copier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public void copyFromTo(File from, File destination) throws IOException {

        Cloud.getInstance().getLogger()
                .debug("Copying template '" + name + "' from " + from.getName() + " to " + destination.getName() + ".");

        File[] files  = from.listFiles();

        for(File file : files) {

            final String fileName
                    = file.getName();

            if(Files.notExists(Path.of(destination + "//" + fileName ))) {
                Files.copy(Paths.get(file.getPath()),
                        Paths.get(destination.getPath() + "//" + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }

        }

    }
}