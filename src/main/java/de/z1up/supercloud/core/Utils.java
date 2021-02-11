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

/**
 * In the Utils class, various globally useful methods a
 * re collected. all methods must be static so that they
 * can be accessed easily.
 *
 * @author Christoph Langer
 * @since 1.0
 */
public class Utils {

    /**
     * Clears the console by spamming 100 clear break
     * lines.
     */
    public static void clearConsole() {
        int i = 0;
        while (i < 100) {
            System.out.println("\n");
            i++;
        }
    }

    /**
     * {@code headerHead()} outputs the upper part of the
     * header. This consists of the ASCII- logo and
     * information about the application.
     */
    private final static void headerHead() {

        final String author =
                Core.getInstance().getInfo().getAuthor();
        final String absv =
                Core.getInstance().getInfo().getAbsoluteVersion();

        System.out.println("   _____                       ________                __\n" +
                "  / ___/__  ______  ___  _____/ ____/ /___  __  ______/ /   by " + author + "\n" +
                "  \\__ \\/ / / / __ \\/ _ \\/ ___/ /   / / __ \\/ / / / __  /    " + absv + "\n" +
                " ___/ / /_/ / /_/ /  __/ /  / /___/ / /_/ / /_/ / /_/ /  \n" +
                "/____/\\__,_/ .___/\\___/_/   \\____/_/\\____/\\__,_/\\__,_/   \n" +
                "          /_/                                            \n");
    }

    /**
     * With the {@code headerOut()} method, the last part
     * of the console header is output. various system and
     * environment information is given out.
     */
    private final static void headerOut() {

        final String absv =
                Core.getInstance().getInfo().getAbsoluteVersion();

        final String repo =
                Core.getInstance().getInfo().getRepo();

        System.out.println(" ╔ Thank you for choosing SuperCloud! You are currently running on " + absv + "! \n" +
                " ╠ For the latest updates, please visit " + repo + "\n" +
                " ╚ If you have any issues, please follow the issue steps or contact a project manager.\n");
    }

    /**
     * The {@code header()} method outputs the entire header.
     * First the console is cleared with {@code clearConsole()}.
     * Then the header part of the header is output with
     * {@code headerHead()}. This is followed by the out part via
     * the {@code headerOut()} method.
     *
     * The system then checks whether a build version of the
     * application is used. If this is not the case, a warning is
     * issued with {@code buildWarning()}.
     *
     */
    public static void header() {

        clearConsole();
        headerHead();
        headerOut();

        if(Core.getInstance()
                .getInfo().checkBuild()) {
            buildWarning();
        }

        clearLine();

    }

    /**
     * Prints out just one clear line.
     */
    private static void clearLine() {
        System.out.println(" ");
    }

    /**
     * This method is called when no build version of the system is
     * used. Only build versions should be used, as only these can
     * be guaranteed to work properly.
     */
    public static void buildWarning() {

        final String repo =
                Core.getInstance().getInfo().getRepo();

        System.out.println("\n ■ You are currently running on a " + Core.getInstance().getInfo().getBuild() + " version of the CloudSystem!\n" +
                " ■ Only build versions can guarantee safe use of the system. Visit \n"+
                " ■ " + repo + " to download the latest build! \n");

    }


    public static void versionWarning() {

        // TODO: Create algorithm to compare version
        //  to latest build version

        // ...

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

    /**
     * Prints out the header which is displayed
     * before custom warnings.
     */
    private static void warningSpacer() {
        System.out.println("\n[ ! ] ■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■ [ ! ]\n");
    }

    /**
     * This method checks whether the system was stopped
     * correctly at the last stop. It does this by asking
     * whether the file shutdown0.log exists in the "logs"
     * folder.
     *
     * Before this, however, it checks whether the system
     * has been started before. If this is the case, a folder
     * named "local" must exist.
     *
     * The shutdown0.log file is created and saved in the
     * shutdown hook of {@link de.z1up.supercloud.cloud.Cloud}.
     *
     * @return The boolean values whether the system was stopped correctly.
     */
    public static boolean wasShutDownGracefully() {

        // check if folder "local" exists
        if(!Files.exists(Paths.get("local"))) {
            return true;
        }

        // check if file "shutdown0.log" exists
        if(Files.exists(Paths.get("logs//shutdown0.log"))) {
            return true;
        }

        // system wasn't shut down properly
        return false;
    }

    /**
     * Appends a new text line to the bottom of a text file.
     *
     * @param file The file which the writer will write into
     * @param text The text that will be appended
     * @throws IOException
     */
    public static void appendFileLine(final File file,
                                      final String text) throws IOException {

        final FileWriter fw
                = new FileWriter(file, true);

        final BufferedWriter bw =
                new BufferedWriter(fw);

        bw.write(text);
        bw.newLine();
        bw.close();
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
