package de.z1up.supercloud.cloud.setup;

public class SetupManager {

    private final ServerSetup serverSetup;
    private final ProxySetup proxySetup;
    private final DefaultsSetup defaultsSetup;

    public SetupManager() {

        this.serverSetup    = new ServerSetup();
        this.proxySetup     = new ProxySetup();
        this.defaultsSetup  = new DefaultsSetup();
    }

    public void loadSetUp() {

        if(!this.proxySetup.isCompleted()) {
            this.proxySetup.runSetUp();
        }

        if(!this.serverSetup.isCompleted()) {
            this.serverSetup.runSetUp();
        }

        if(!this.defaultsSetup.isCompleted()) {
            this.defaultsSetup.runSetUp();
        }
    }
}
