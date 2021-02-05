package de.z1up.supercloud.cloud.setup;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.Downloader;
import de.z1up.supercloud.core.input.InputReader;
import de.z1up.supercloud.core.interfaces.SetUp;

import java.io.File;

public class ProxySetup implements SetUp ***REMOVED***

    private final String[] PROXY_VERSIONS       = ***REMOVED***"bungeecord", "waterfall"***REMOVED***;
    private final String BUNGEECORD_URL         = "https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar";
    private final String FILE_NAME              = "proxy.jar";
    private final String PATH                   = "local//lib";

    private boolean completed;

    @Override
    public void runSetUp() ***REMOVED***

        checkCompleted();

        if(isCompleted()) ***REMOVED***
            return;
        ***REMOVED***

        askProxyVersion();

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

    private void askProxyVersion() ***REMOVED***

        Cloud.getInstance().getLogger()
                .log("§7Please select a Proxy Version: (Possible: " + getProxyVersions() + ")");

        InputReader reader = new InputReader();
        reader.open();

        String input = reader.getInput();

        if(!existsProxyVersion(input)) ***REMOVED***
            Cloud.getInstance().getLogger()
                    .log("§4Ooops! This proxy version doesn't exist!");
            askProxyVersion();
            return;
        ***REMOVED***

        Downloader downloader = null;

        if(input.equalsIgnoreCase("bungeecord")) ***REMOVED***

            downloader = new Downloader(BUNGEECORD_URL, PATH, FILE_NAME);

        ***REMOVED***

        if(downloader != null) ***REMOVED***

            Cloud.getInstance().getLogger()
                    .log("§aDownloading " + FILE_NAME + "...");
            downloader.downloadAsync();

        ***REMOVED*** else ***REMOVED***

            Cloud.getInstance().getLogger()
                    .log("§4Error! Download couldn't be started!");

        ***REMOVED***

    ***REMOVED***

    private boolean existsProxyVersion(String input) ***REMOVED***

        boolean exists = false;

        for (String proxy_version : PROXY_VERSIONS) ***REMOVED***
            if(input.equalsIgnoreCase(proxy_version)) ***REMOVED***
                exists = true;
            ***REMOVED***
        ***REMOVED***

        return exists;
    ***REMOVED***

    private String getProxyVersions() ***REMOVED***

        String versions = "";

        for (String proxy_version : PROXY_VERSIONS) ***REMOVED***
            versions = versions + "\"" + proxy_version + "\", ";
        ***REMOVED***

        return versions;
    ***REMOVED***
***REMOVED***