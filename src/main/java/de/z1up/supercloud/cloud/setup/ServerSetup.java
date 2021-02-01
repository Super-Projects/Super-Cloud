package de.z1up.supercloud.cloud.setup;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.Downloader;
import de.z1up.supercloud.core.input.InputReader;
import de.z1up.supercloud.core.interfaces.SetUp;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerSetup implements SetUp {

    private final String[] VERSIONS_SERVER  = {"spigot", "paper"};
    private final String[] VERSIONS_SPIGOT  = {"1.8.8", "1.9", "1.11"};
    private final String SPIGOT_URL         = "https://cdn.getbukkit.org/spigot/spigot-";
    private final String FILE_NAME          = "server.jar";
    private final String PATH               = "local//lib";

    private boolean completed;
    private String serverVersion;

    @Override
    public void runSetUp() {

        checkCompleted();

        if(isCompleted()) {
            return;
        }

        askServerVersion();

    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void checkCompleted() {

        File file = new File(PATH + "//" + FILE_NAME);

        if(file.exists()) {
            completed = true;
        } else {
            completed = false;
        }

    }

    private void askServerVersion() {

        Cloud.getInstance().getLogger()
                .log("Please select a server version: (Possible: " + getServerVersions() + ")");

        InputReader reader = new InputReader();
        reader.open();

        String input = reader.getInput();

        if(!existsServerVersion(input)) {
            Cloud.getInstance().getLogger()
                    .log("§4Ooops! This server version doesn't exists!");
            askServerVersion();
            return;
        }

        if(input.equalsIgnoreCase("spigot")) {
            askSpigotVersion();
        }

    }

    private void askSpigotVersion() {

        Cloud.getInstance().getLogger()
                .log("Please select a spigot version: (Possible: " + getSpigotVersions() + ")");

        InputReader reader = new InputReader();
        reader.open();

        String input = reader.getInput();


        if(!existsSpigotVersion(input)) {
            Cloud.getInstance().getLogger()
                    .log("§4Ooops! This server spigot version doesn't exists!");
            askSpigotVersion();
            return;
        }

        Downloader downloader = new Downloader(SPIGOT_URL + input + "-R0.1-SNAPSHOT-latest.jar", PATH, FILE_NAME);

        if(downloader != null) {

            Cloud.getInstance().getLogger()
                    .log("§aDownloading " + FILE_NAME + "...");
            downloader.downloadAsync();


        } else {

            Cloud.getInstance().getLogger()
                    .log("§4Error! Download couldn't be started!");

        }

    }

    private boolean existsServerVersion(String input) {

        AtomicBoolean exists = new AtomicBoolean(false);

        Arrays.stream(VERSIONS_SERVER).forEach(version -> {
            if(version.equalsIgnoreCase(input))
                exists.set(true);
        });

        return exists.get();
    }

    private boolean existsSpigotVersion(String input) {

        AtomicBoolean exists = new AtomicBoolean(false);

        Arrays.stream(VERSIONS_SPIGOT).forEach(version -> {
            if(version.equalsIgnoreCase(input))
                exists.set(true);
        });

        return exists.get();
    }

    private String getServerVersions() {

        String versions = "";

        for (String s : VERSIONS_SERVER) {
            versions = versions + "\"" + s + "\", ";
        }
        return versions;
    }

    private String getSpigotVersions() {

        String versions = "";

        for (String s : VERSIONS_SPIGOT) {
            versions = versions + "\"" + s + "\", ";
        }
        return versions;
    }

}
