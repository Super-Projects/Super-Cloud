package de.z1up.supercloud;

import de.z1up.supercloud.core.Core;

/**
 * The launcher class is the main class of the
 * cloud system. The main method that starts the
 * programme is located here.
 *
 * @author      Christoph Langer
 * @since       1.0
 */
public class Launcher {

    /**
     * In the main method, the system checks whether
     * the correct java version is being used. If
     * this is the case, the startUp method of the
     * core is called.
     *
     * @param   args
     *          Arguments passed when running the application
     */
    public static void main(String[] args) {

        // check if using right version
        if (Float.parseFloat(System.getProperty("java.class.version")) < 55D) {
            System.out.println("[!] Please update you Java version! " +
                    "The cloud can only run on Java version 11 and above! [!]");
            return;
        }

        // load the core
        new Core().startUp();

    }

}
