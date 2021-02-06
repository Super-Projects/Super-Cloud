package de.z1up.supercloud;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.Core;
import de.z1up.supercloud.core.time.CloudThread;

public class Launcher {

    public static void main(String[] args) {

        // check if using right version
        if (Float.parseFloat(System.getProperty("java.class.version")) < 55D) {
            for(int i = 0; i < 100; i++) {
                System.out.println("");
            }
            System.out.println("[!] Please update you Java version! The cloud can only run on Java version 11 and above! [!]");
            return;
        }

        // add the shutdown hook
        shutdownHook();

        // load the core
        new Core().startUp();

    }

    private static void shutdownHook() {

        Thread hook = new Thread(() -> {
            Cloud.getInstance().shutdownGracefully();
        });

        Runtime.getRuntime().addShutdownHook(hook);

    }

}
