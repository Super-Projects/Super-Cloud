package de.z1up.supercloud.cloud.setup;

public class SetupManager {

    private ServerSetup serverSetup;
    private ProxySetup proxySetup;
    private DefaultsSetup defaultsSetup;

    public void startSetUp() {

        init();

        serverSetup.runSetUp();
        proxySetup.runSetUp();
        defaultsSetup.runSetUp();

    }

    void init() {
        serverSetup = new ServerSetup();
        proxySetup = new ProxySetup();
        defaultsSetup = new DefaultsSetup();
    }

}
