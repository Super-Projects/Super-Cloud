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
        while (i < 200) {
            System.out.println(" ");
            i++;
        }

    }

    private static void headerHead() {

        System.out.println(" __ __      _____                             _____  _                    _  __ __   ");
        System.out.println(" \\ \\\\ \\    / ____|                           / ____|| |                  | | \\ \\\\ \\  ");
        System.out.println("  \\ \\\\ \\  | (___   _   _  _ __    ___  _ __ | |     | |  ___   _   _   __| |  \\ \\\\ \\ ");
        System.out.println("   > >> >  \\___ \\ | | | || '_ \\  / _ \\| '__|| |     | | / _ \\ | | | | / _` |   > >> >");
        System.out.println("  / // /   ____) || |_| || |_) ||  __/| |   | |____ | || (_) || |_| || (_| |  / // / ");
        System.out.println(" /_//_/   |_____/  \\__,_|| .__/  \\___||_|    \\_____||_| \\___/  \\__,_| \\__,_| /_//_/  ");
        System.out.println("                         | |                                                         ");
        System.out.println("                         |_|                                                         ");
        System.out.println("\n");

    }

    private static void headerOut() {
        System.out.println("«» SuperCloud version b0.3.4");
        System.out.println("«» Running on Java " + System.getProperty("java.version") + "...");
        System.out.println("«» " + System.getProperty("user.name") + "@" + System.getProperty("os.name"));
    }

    public static void header() {
        clearConsole();
        headerHead();
        headerOut();
    }

    public static void warningNotShutdownGracefully() {

        System.out.println("[!] WARNING [!]");
        System.out.println("[!] The last time the cloud was stopped, it was not shut down properly. " +
                "This can lead to problems that cause the cloud to crash. " +
                "Please use '/stop' to stop the cloud next time.");
        System.out.println("[!] WARNING [!]");

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
