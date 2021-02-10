package de.z1up.supercloud.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

public class Utils {

    public static void clearConsole() {

        int i = 0;
        while (i < 150) {
            System.out.println("\n");
            i++;
        }

    }

    private static void headerHead() {

        System.out.println("   _____                       ________                __\n" +
                "  / ___/__  ______  ___  _____/ ____/ /___  __  ______/ /   by " + Core.getInstance().getInfo().getAuthor() + "\n" +
                "  \\__ \\/ / / / __ \\/ _ \\/ ___/ /   / / __ \\/ / / / __  /    " + Core.getInstance().getInfo().getAbsoluteVersion() + "\n" +
                " ___/ / /_/ / /_/ /  __/ /  / /___/ / /_/ / /_/ / /_/ /  \n" +
                "/____/\\__,_/ .___/\\___/_/   \\____/_/\\____/\\__,_/\\__,_/   \n" +
                "          /_/                                            \n");
    }

    private static void headerOut() {
        System.out.println(" ╔ Thank you for choosing SuperCloud! You are currently running on "
                + Core.getInstance().getInfo().getAbsoluteVersion() + "! \n" +
                " ╠ For the latest updates, please visit " + Core.getInstance().getInfo().getRepo() + "\n" +
                " ╚ If you have any issues, please follow the issue steps or contact a project manager.\n");

        // ■■
    }

    public static void header() {
        clearConsole();
        headerHead();
        headerOut();

        if(Core.getInstance()
                .getInfo().checkBuild()) {
            buildWarning();
        }

        headerFinal();

    }

    private static void headerFinal() {
        System.out.println(" ");
    }

    public static void buildWarning() {

        System.out.println("\n ■ You are currently running on a " + Core.getInstance().getInfo().getBuild() + " version of the CloudSystem!\n" +
                " ■ Only build versions can guarantee safe use of the system. Visit \n"+
                " ■ " + Core.getInstance().getInfo().getRepo() + " to download the latest build! \n");

    }

    public static void versionWarning() {

        System.out.println("\n ■ You are currently running on a " + Core.getInstance().getInfo().getBuild() + " version of \n" +
                " the CloudSystem! Only build versions can guarantee safe use of the system. Visit"+
                "\n " + Core.getInstance().getInfo().getRepo() + " to download the latest build! ■");

    }

    public static void warningNotShutdownGracefully() {

        warningSpacer();
        System.out.println("  The last time the cloud was stopped, it was not shut down properly. \n" +
                "  This can lead to problems that cause the cloud to crash. \n" +
                "  Please use '/stop' to stop the cloud next time.");
        warningSpacer();

    }

    public static void warningNoDBAccessData() {

        warningSpacer();
        System.out.println("  To use the cloud system, a MongoDB database is required. After the first\n" +
                "  start, a file 'mongo.json' was created in the folder 'mongo'. Please enter your \n" +
                "  access data there and restart the cloud afterwards.");
        warningSpacer();

    }

    public static void warningWrongDBAccessData() {

        warningSpacer();
        System.out.println("  Timed out while connecting to the database. Please check your access data \n" +
                "  in the 'mongo.json' file and restart the cloud afterwards.");
        warningSpacer();

    }

    private static void warningSpacer() {
        System.out.println("\n[ ! ] ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ [ ! ]\n");
    }

    public static boolean wasShutDownGracefully() {

        if(!Files.exists(Paths.get("local"))) {
            return true;
        }

        if(Files.exists(Paths.get("logs//shutdown0.log"))) {
            return true;
        }

        return false;
    }

    public static void addFileLine(File file, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(file, true));
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static ProcessHandle getProcess(final long pid) {

        if(existsProcess(pid)) {

            Optional<ProcessHandle> processHandle
                    = ProcessHandle.of(pid);

            if(!processHandle.isPresent()) {
                return null;
            }

            if(processHandle.isEmpty()) {
                return null;
            }

            return processHandle.get();

        }
        return null;
    }

    public static boolean existsProcess(final long pid) {

        Optional<ProcessHandle> processHandle
                = ProcessHandle.of(pid);

        if(processHandle.isPresent()) {
            return true;
        }

        return false;
    }

    public static void deleteDirectory(Path path) throws IOException {

        File file = path.toFile();

        if(file.exists()) {

            if(file.isDirectory()) {

                for(File content : Objects.requireNonNull(file.listFiles())) {

                    if(!content.isDirectory()) {
                        Files.delete(Path.of(content.getPath()));
                    } else {
                        deleteDirectory(Path.of(content.getPath()));

                        if(Objects.requireNonNull(content.listFiles()).length == 0) {
                            Files.delete(Path.of(content.getPath()));
                        }

                    }

                }

            }

        }

    }

}
