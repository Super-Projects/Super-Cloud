package de.z1up.supercloud.core;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.input.CloudInfo;

public class Core {

    private static Core instance;
    private Cloud cloud;
    private CloudInfo info;

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
        this.info = CloudInfo.load();
        this.cloud = new Cloud();
    }

    private void load() {
        this.cloud.run();
    }

    public CloudInfo getInfo() {
        return info;
    }

    public Cloud getCloud() {
        return cloud;
    }
}
