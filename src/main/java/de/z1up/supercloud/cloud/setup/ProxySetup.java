package de.z1up.supercloud.cloud.setup;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.Downloader;
import de.z1up.supercloud.core.input.InputReader;
import de.z1up.supercloud.core.interfaces.SetUp;

import java.io.File;

public class ProxySetup implements SetUp {

    private final String[] PROXY_VERSIONS       = {"bungeecord", "waterfall"};
    private final String BUNGEECORD_URL         = "https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar";
    private final String FILE_NAME              = "proxy.jar";
    private final String PATH                   = "local//lib";

    private boolean completed;

    @Override
    public void runSetUp() {

        checkCompleted();

        if(isCompleted()) {
            return;
        }

        askProxyVersion();

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

    private void askProxyVersion() {

        Cloud.getInstance().getLogger()
                .log("§7Please select a Proxy Version: (Possible: " + getProxyVersions() + ")");

        InputReader reader = new InputReader();
        reader.open();

        String input = reader.getInput();

        if(!existsProxyVersion(input)) {
            Cloud.getInstance().getLogger()
                    .log("§4Ooops! This proxy version doesn't exist!");
            askProxyVersion();
            return;
        }

        Downloader downloader = null;

        if(input.equalsIgnoreCase("bungeecord")) {

            downloader = new Downloader(BUNGEECORD_URL, PATH, FILE_NAME);

        }

        if(downloader != null) {

            Cloud.getInstance().getLogger()
                    .log("§aDownloading " + FILE_NAME + "...");
            downloader.downloadAsync();

        } else {

            Cloud.getInstance().getLogger()
                    .log("§4Error! Download couldn't be started!");

        }

    }

    private boolean existsProxyVersion(String input) {

        boolean exists = false;

        for (String proxy_version : PROXY_VERSIONS) {
            if(input.equalsIgnoreCase(proxy_version)) {
                exists = true;
            }
        }

        return exists;
    }

    private String getProxyVersions() {

        String versions = "";

        for (String proxy_version : PROXY_VERSIONS) {
            versions = versions + "\"" + proxy_version + "\", ";
        }

        return versions;
    }
}