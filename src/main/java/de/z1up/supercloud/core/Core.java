package de.z1up.supercloud.core;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.input.CloudInfo;
import de.z1up.supercloud.core.settings.Setting;
import de.z1up.supercloud.core.settings.SettingsManager;

/**
 * In the core, the cloud class that bootstraps the
 * system is initialised and started. In addition,
 * the cloud information is loaded here via the
 * {@link CloudInfo} class.
 *
 * @author  Christoph Langer
 * @since   1.0
 *
 * @see     Cloud
 * @see     CloudInfo
 */
public class Core {

    private static Core     instance;
    private Cloud           cloud;
    private CloudInfo       info;

    public Core() {
        instance = this;
    }

    public static Core getInstance() {
        return instance;
    }

    public void startUp() {
        this.init();
        this.load();
    }

    void init() {
        this.info = CloudInfo.load();
        this.cloud = new Cloud();
    }

    private void load() {
        this.cloud.run();
    }

    public CloudInfo getInfo() {
        return this.info;
    }

    public Cloud getCloud() {
        return this.cloud;
    }

    // Settings

    private SettingsManager settingsManager;

    public void loadSettingsManager() {
        this.settingsManager = new SettingsManager();
    }

    public SettingsManager getSettingsManager() {
        return this.settingsManager;
    }
}
