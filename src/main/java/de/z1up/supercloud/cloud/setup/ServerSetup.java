package de.z1up.supercloud.cloud.setup;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.Downloader;
import de.z1up.supercloud.core.input.InputReader;
import de.z1up.supercloud.core.interfaces.SetUp;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerSetup implements SetUp ***REMOVED***

    private final String[] VERSIONS_SERVER  = ***REMOVED***"spigot", "paper"***REMOVED***;
    private final String[] VERSIONS_SPIGOT  = ***REMOVED***"1.8.8", "1.9", "1.11"***REMOVED***;
    private final String SPIGOT_URL         = "https://cdn.getbukkit.org/spigot/spigot-";
    private final String FILE_NAME          = "server.jar";
    private final String PATH               = "local//lib";

    private boolean completed;
    private String serverVersion;

    @Override
    public void runSetUp() ***REMOVED***

        checkCompleted();

        if(isCompleted()) ***REMOVED***
            return;
        ***REMOVED***

        askServerVersion();

    ***REMOVED***

    @Override
    public boolean isCompleted() ***REMOVED***
        return completed;
    ***REMOVED***

    @Override
    public void checkCompleted() ***REMOVED***

        File file = new File(PATH + "//" + FILE_NAME);

        if(file.exists()) ***REMOVED***
            completed = true;
        ***REMOVED*** else ***REMOVED***
            completed = false;
        ***REMOVED***

    ***REMOVED***

    private void askServerVersion() ***REMOVED***

        Cloud.getInstance().getLogger()
                .log("Please select a server version: (Possible: " + getServerVersions() + ")");

        InputReader reader = new InputReader();
        reader.open();

        String input = reader.getInput();

        if(!existsServerVersion(input)) ***REMOVED***
            Cloud.getInstance().getLogger()
                    .log("§4Ooops! This server version doesn't exists!");
            askServerVersion();
            return;
        ***REMOVED***

        if(input.equalsIgnoreCase("spigot")) ***REMOVED***
            askSpigotVersion();
        ***REMOVED***

    ***REMOVED***

    private void askSpigotVersion() ***REMOVED***

        Cloud.getInstance().getLogger()
                .log("Please select a spigot version: (Possible: " + getSpigotVersions() + ")");

        InputReader reader = new InputReader();
        reader.open();

        String input = reader.getInput();


        if(!existsSpigotVersion(input)) ***REMOVED***
            Cloud.getInstance().getLogger()
                    .log("§4Ooops! This server spigot version doesn't exists!");
            askSpigotVersion();
            return;
        ***REMOVED***

        Downloader downloader = new Downloader(SPIGOT_URL + input + "-R0.1-SNAPSHOT-latest.jar", PATH, FILE_NAME);

        if(downloader != null) ***REMOVED***

            Cloud.getInstance().getLogger()
                    .log("§aDownloading " + FILE_NAME + "...");
            downloader.downloadAsync();


        ***REMOVED*** else ***REMOVED***

            Cloud.getInstance().getLogger()
                    .log("§4Error! Download couldn't be started!");

        ***REMOVED***

    ***REMOVED***

    private boolean existsServerVersion(String input) ***REMOVED***

        AtomicBoolean exists = new AtomicBoolean(false);

        Arrays.stream(VERSIONS_SERVER).forEach(version -> ***REMOVED***
            if(version.equalsIgnoreCase(input))
                exists.set(true);
        ***REMOVED***);

        return exists.get();
    ***REMOVED***

    private boolean existsSpigotVersion(String input) ***REMOVED***

        AtomicBoolean exists = new AtomicBoolean(false);

        Arrays.stream(VERSIONS_SPIGOT).forEach(version -> ***REMOVED***
            if(version.equalsIgnoreCase(input))
                exists.set(true);
        ***REMOVED***);

        return exists.get();
    ***REMOVED***

    private String getServerVersions() ***REMOVED***

        String versions = "";

        for (String s : VERSIONS_SERVER) ***REMOVED***
            versions = versions + "\"" + s + "\", ";
        ***REMOVED***
        return versions;
    ***REMOVED***

    private String getSpigotVersions() ***REMOVED***

        String versions = "";

        for (String s : VERSIONS_SPIGOT) ***REMOVED***
            versions = versions + "\"" + s + "\", ";
        ***REMOVED***
        return versions;
    ***REMOVED***

***REMOVED***
