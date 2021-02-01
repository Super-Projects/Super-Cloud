package de.z1up.supercloud.cloud.setup;

public class SetupManager ***REMOVED***

    private ServerSetup serverSetup;
    private ProxySetup proxySetup;
    private DefaultsSetup defaultsSetup;

    public void startSetUp() ***REMOVED***

        init();

        serverSetup.runSetUp();
        proxySetup.runSetUp();
        defaultsSetup.runSetUp();

    ***REMOVED***

    void init() ***REMOVED***
        serverSetup = new ServerSetup();
        proxySetup = new ProxySetup();
        defaultsSetup = new DefaultsSetup();
    ***REMOVED***

***REMOVED***
