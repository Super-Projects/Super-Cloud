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
 * @author  Christoph Langer
 * @since   1.0
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

        System.out.println(
                " ╔ Thank you for choosing SuperCloud! You are currently running on " + absv + "! \n" +
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

        System.out.println("\n ■ You are currently running on a "
                + Core.getInstance().getInfo().getBuild() + " version of the CloudSystem!\n" +
                " ■ Only build versions can guarantee safe use of the system. Visit \n"+
                " ■ " + repo + " to download the latest build! \n");

    }


    /**
     * Issues a warning that the version of the system is out of
     * date and should be updated.
     *
     * Before and after warnings, the {@code warningSpacer()}
     * method is always called to highlight the message.
     */
    public static void versionWarning() {

        /*
         TODO: Create algorithm to compare version
          to latest build version
         ...
        */

    }

    /**
     * Issues a warning message that the cloud system was not
     * shut down in the direction at the last stop. This should
     * not happen, otherwise errors may occur.
     *
     * Before and after warnings, the {@code warningSpacer()}
     * method is always called to highlight the message.
     */
    public static void warningNotShutdownGracefully() {

        warningSpacer();
        System.out.println("  The last time the cloud was stopped, it was not shut down properly. \n" +
                "  This can lead to problems that cause the cloud to crash. \n" +
                "  Please use '/stop' to stop the cloud next time.");
        warningSpacer();

    }

    /**
     * Issues a warning message that there is no access
     * data to the database. In this case, the system must
     * be stopped, as it can only function with a database.
     *
     * Before and after warnings, the {@code warningSpacer()}
     * method is always called to highlight the message.
     */
    public static void warningNoDBAccessData() {

        warningSpacer();
        System.out.println("  To use the cloud system, a MongoDB database is required. After the first\n" +
                "  start, a file 'mongo.json' was created in the folder 'mongo'. Please enter your \n" +
                "  access data there and restart the cloud afterwards.");
        warningSpacer();

    }

    /**
     * Issues a warning message that there is incorrect
     * access data to the database.
     *
     * Before and after warnings, the {@code warningSpacer()}
     * method is always called to highlight the message.
     */
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
     * @return  The boolean values whether the system was
     *          stopped correctly.
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
     * @param   file
     *          The file which the writer will write the given
     *          {@code text} into.
     *
     * @param   text
     *          The text that will be appended to the given file.
     *
     * @throws  IOException
     *          Signals that an I/O exception of some sort has
     *          occurred. Might be thrown when a file doesn't
     *          exists or isn't writeable.
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

    /**
     * Returns a ProcessHandle of a process based on the pid. First,
     * the {@code existsProcess()} method is used to query whether
     * the process exists at all. If this is the case, the static
     * {@code .of()} method is used. method of the ProcessHandle
     * filters out the new ProcessHandle.
     *
     * @param   pid
     *          The pid of the Process which needs to be checked.
     *
     * @return  The boolean value whether the process exits.
     */
    public static ProcessHandle getProcess(final long pid) {

        if(existsProcess(pid)) {

            Optional<ProcessHandle> processHandle
                    = ProcessHandle.of(pid);

            return processHandle.get();

        }
        return null;
    }

    /**
     * Checks whether a process exists in the system or is
     * alive using the ProcessID. Access is via the ProcessHandle.
     *
     * @param   pid
     *          The pid of the Process which needs to be checked.
     *
     * @return  The boolean value whether the process exits.
     */
    public static boolean existsProcess(final long pid) {

        Optional<ProcessHandle> processHandle
                = ProcessHandle.of(pid);

        if(processHandle.isPresent()) {
            return true;
        }

        if(processHandle.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Deletes a directory with all its contents. If the file
     * exists and is a directory, all files in the directory are
     * deleted. If the directory contains a subdirectory, the
     * {@code deleteDirectory()} method is also applied to the
     * subdirectory.
     *
     * @param   path
     *          The path to the directory which will be deleted
     *
     * @throws  IOException
     *          Signals that an I/O exception of some sort has
     *          occurred. Might be thrown when a file doesn't
     *          exists or isn't writeable.
     */
    public static void deleteDirectory(final Path path) throws IOException {

        final File file
                = path.toFile();

        if(!file.exists()) {
            return;
        }

        if(!file.isDirectory()) {
            return;
        }

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
