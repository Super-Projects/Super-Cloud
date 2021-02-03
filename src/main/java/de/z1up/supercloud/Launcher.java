package de.z1up.supercloud;

import de.z1up.supercloud.core.Core;
import de.z1up.supercloud.core.mongo.MongoManager;
import org.json.JSONObject;

public class Launcher {

    public static void main(String[] args) {

        header();

    }

    public static void header() {
        System.out.println(" __ __      _____                             _____  _                    _  __ __   ");
        System.out.println(" \\ \\\\ \\    / ____|                           / ____|| |                  | | \\ \\\\ \\  ");
        System.out.println("  \\ \\\\ \\  | (___   _   _  _ __    ___  _ __ | |     | |  ___   _   _   __| |  \\ \\\\ \\ ");
        System.out.println("   > >> >  \\___ \\ | | | || '_ \\  / _ \\| '__|| |     | | / _ \\ | | | | / _` |   > >> >");
        System.out.println("  / // /   ____) || |_| || |_) ||  __/| |   | |____ | || (_) || |_| || (_| |  / // / ");
        System.out.println(" /_//_/   |_____/  \\__,_|| .__/  \\___||_|    \\_____||_| \\___/  \\__,_| \\__,_| /_//_/  ");
        System.out.println("                         | |                                                         ");
        System.out.println("                         |_|                                                         ");
        headerOut0();
    }

    private static void headerOut0() {
        System.out.println("«» SuperCloud version b0.3.4");
        System.out.println("«» Running on Java " + System.getProperty("java.version") + "...");
        System.out.println("«» " + System.getProperty("user.name") + "@" + System.getProperty("os.name"));
        System.out.println(" ");
    }

    public static void headerOut() {
        headerOut0();
    }

    /*
    public static void header() {
        System.out.println(" __ __      _____                             _____  _                    _  __ __   ");
        System.out.println(" \\ \\\\ \\    / ____|                           / ____|| |                  | | \\ \\\\ \\  ");
        System.out.println("  \\ \\\\ \\  | (___   _   _  _ __    ___  _ __ | |     | |  ___   _   _   __| |  \\ \\\\ \\ ");
        System.out.println("   > >> >  \\___ \\ | | | || '_ \\  / _ \\| '__|| |     | | / _ \\ | | | | / _` |   > >> >");
        System.out.println("  / // /   ____) || |_| || |_) ||  __/| |   | |____ | || (_) || |_| || (_| |  / // / ");
        System.out.println(" /_//_/   |_____/  \\__,_|| .__/  \\___||_|    \\_____||_| \\___/  \\__,_| \\__,_| /_//_/  ");
        System.out.println("                         | |                                                         ");
        System.out.println("                         |_|                                                         ");
        headerOut0();
    }


    private static void headerOut0() {

        System.out.println("«» SuperCloud version b0.3.4");
        System.out.println("«» Running on Java " + System.getProperty("java.version") + "...");
        System.out.println("«» " + System.getProperty("user.name") + "@" + System.getProperty("os.name") + " " + System.getProperty("os.version"));
    }

    public static void headerOut() {
        headerOut0();
    }
    */

    db.createUser(
    {
        user: "chris23lngrAdmin",
                pwd: "#CW", // or cleartext password
            roles: [ { role: "userAdminAnyDatabase", db: "admin" }, "readWriteAnyDatabase" ]
    }
)


}
