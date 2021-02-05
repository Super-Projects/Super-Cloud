package de.z1up.supercloud.cloud.setup;

public class SetupManager ***REMOVED***

    private final ServerSetup serverSetup;
    private final ProxySetup proxySetup;
    private final DefaultsSetup defaultsSetup;

    public SetupManager() ***REMOVED***

        this.serverSetup    = new ServerSetup();
        this.proxySetup     = new ProxySetup();
        this.defaultsSetup  = new DefaultsSetup();
    ***REMOVED***

    public void loadSetUp() ***REMOVED***

        if(!this.proxySetup.isCompleted()) ***REMOVED***
            this.proxySetup.runSetUp();
        ***REMOVED***

        if(!this.serverSetup.isCompleted()) ***REMOVED***
            this.serverSetup.runSetUp();
        ***REMOVED***

        if(!this.defaultsSetup.isCompleted()) ***REMOVED***
            this.defaultsSetup.runSetUp();
        ***REMOVED***
    ***REMOVED***
***REMOVED***
