package de.z1up.supercloud.core;

import de.z1up.supercloud.cloud.Cloud;

public class Core ***REMOVED***

    private static Core instance;
    private Cloud cloud;

    public Core() ***REMOVED***
        instance = this;
    ***REMOVED***

    public static Core getInstance() ***REMOVED***
        return instance;
    ***REMOVED***

    public void startUp() ***REMOVED***
        init();
        load();
    ***REMOVED***

    void init() ***REMOVED***
        this.cloud = new Cloud();
    ***REMOVED***

    private void load() ***REMOVED***
        this.cloud.run();
    ***REMOVED***
***REMOVED***
