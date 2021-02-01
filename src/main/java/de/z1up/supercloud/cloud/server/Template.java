package de.z1up.supercloud.cloud.server;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.file.Copier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Template ***REMOVED***

    private final String PATH = "local//templates";
    private String name;

    public Template(String name) ***REMOVED***
        this.name = name;
    ***REMOVED***

    public String getName() ***REMOVED***
        return name;
    ***REMOVED***

    public void create() ***REMOVED***

        File dir = new File(PATH + "//" + name);

        if(!dir.exists()) ***REMOVED***
            dir.mkdirs();
        ***REMOVED***

        File serverProperties = new File(PATH + "//" + name + "//server.properties");

        if(!serverProperties.exists()) ***REMOVED***
            try ***REMOVED***
                serverProperties.createNewFile();
            ***REMOVED*** catch (IOException exception) ***REMOVED***
                exception.printStackTrace();
            ***REMOVED***
        ***REMOVED***

        File pluginsFolder = new File(PATH + "//" + name + "//plugins");

        if(!pluginsFolder.exists()) ***REMOVED***
            pluginsFolder.mkdirs();
        ***REMOVED***

    ***REMOVED***

    public Collection<File> getFiles() ***REMOVED***

        Collection files = new ArrayList();

        File dir = new File(PATH + "//" + name);

        File[] contents = dir.listFiles();

        for (File content : contents) ***REMOVED***

            if(content.exists()) ***REMOVED***
                files.add(content);
            ***REMOVED***

        ***REMOVED***
        return files;
    ***REMOVED***

    public Collection<File> getFiles(String path) ***REMOVED***

        Collection files = new ArrayList();

        File dir = new File(path);

        File[] contents = dir.listFiles();

        for (File content : contents) ***REMOVED***

            if(content.exists()) ***REMOVED***
                files.add(content);
            ***REMOVED***

        ***REMOVED***
        return files;
    ***REMOVED***

    /*
    public void copyTo(CloudFolder folder) ***REMOVED***

        Collection<File> files = getFiles();
        Iterator<File> fileIterator = files.iterator();

        Path copyTo = Paths.get(folder.getPath());

        while (fileIterator.hasNext()) ***REMOVED***

            File file = fileIterator.next();
            Path filePath = Paths.get(file.getPath());

            try ***REMOVED***
                Files.copy(filePath, copyTo, StandardCopyOption.REPLACE_EXISTING);
            ***REMOVED*** catch (IOException exception) ***REMOVED***
                exception.printStackTrace();
            ***REMOVED***

            if(file.isDirectory()) ***REMOVED***

            ***REMOVED***

        ***REMOVED***
    ***REMOVED****/

    public void copyFromTo(CloudFolder from, CloudFolder to) ***REMOVED***

        Cloud.getInstance().getLogger().debug("Copying template" + name + "...");

        try ***REMOVED***
            Copier.copyDirectoryCompatibityMode(from.get(), to.get());
        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***

    ***REMOVED***
***REMOVED***