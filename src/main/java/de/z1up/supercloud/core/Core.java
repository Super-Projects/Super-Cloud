package de.z1up.supercloud.core;

import de.z1up.supercloud.cloud.Cloud;

public class Core {

    private static Core instance;
    private Cloud cloud;

    public Core() {
        instance = this;
    }

    public static Core getInstance() {
        return instance;
    }

    public void startUp() {
        init();
        load();
    }

    void init() {
        this.cloud = new Cloud();
    }

    public void load() {
        this.cloud.run();
    }
}
