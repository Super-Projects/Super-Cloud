package de.z1up.supercloud.cloud.server.obj;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.core.Utils;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.screen.Screen;
import de.z1up.supercloud.core.time.CloudThread;

import java.io.*;
import java.nio.file.*;
import java.util.Objects;

public class GameServer extends Server {

    private UID threadID;
    private transient Screen screen;

    public GameServer(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path, boolean connected, int port, int maxPlayers, String motd) {
        super(uid, serverType, serverMode, display, group, maintenance, id, path, connected, port, maxPlayers, motd);
    }

    @Override
    public void bootstrap() {

        final String command = "java " +
                "-XX:+UseG1GC " +
                "-XX:MaxGCPauseMillis=50 " +
                "-XX:MaxPermSize=256M " +
                "-XX:-UseAdaptiveSizePolicy " +
                "-XX:CompileThreshold=100 " +
                "-Dcom.mojang.eula.agree=true " +
                "-Dio.netty.recycler.maxCapacity=0 " +
                "-Dio.netty.recycler.maxCapacity.default=0 " +
                "-Djline.terminal=jline.UnsupportedTerminal " +
                "-Xmx1G " +
                "-jar server.jar nogui";

        final CloudThread thread = new CloudThread() {
            @Override
            public void run() {

                GameServer server = GameServer.this;

                try {

                    Cloud.getInstance().getLogger().log("Starting server");

                    final Process process
                            = Runtime.getRuntime().exec(command, null, new CloudFolder(server.getPath()).get());

                    server.setPid(process.pid());
                    server.bootstrapAfter(process);

                } catch (IOException exception) {
                    exception.printStackTrace();
                }


            }
        };

        Cloud.getInstance().getThreadManager()
                .createThread(thread);

        try {
            this.createEnvironment();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.threadID = thread.getUniqueID();


        thread.start();
    }

    private void bootstrapAfter(Process process) {

        this.setConnected(true);

        this.screen = new Screen(process.getInputStream(),
                this.getDisplay(), true);

        System.out.println("pid: " + getPid());

        super.update();
    }

    @Override
    public void shutdown() {

        // check if thread uid even exists
        if(this.threadID == null) {
            Cloud.getInstance().getLogger()
                    .debug("no thread id found for server " + this.getUid().getTag());
            return;
        }

        CloudThread thread = Cloud.getInstance()
                .getThreadManager().getThread(this.threadID);

        // check if thread uid even exists
        if(thread == null) {
            Cloud.getInstance().getLogger()
                    .debug("no thread found for server " + this.getUid().getTag());
            return;
        }

        final String threadTag
                = this.getThread().getUniqueID().getTag();


        // close the screen
        if(this.screen == null) {
            Cloud.getInstance().getLogger()
                    .debug("no screen found for " + this.getDisplay());
        } else {
            this.screen.close();
        }


        // destroy the server process

        /*
        if(this.process != null) {

            if(this.process.isAlive()) {

                try {

                    this.process.wait(1000);
                    this.process.destroy();

                } catch (InterruptedException exc) {

                    Cloud.getInstance().getLogger()
                            .debug(exc.getMessage());
                    this.destroyForcibly();

                }

            } else {
                Cloud.getInstance().getLogger()
                        .debug("process in thread " + threadTag + " isn't alive");
            }

        } else {
            Cloud.getInstance().getLogger()
                    .debug("process in thread " + threadTag + " doesn't exists");
        }

         */


        // finally shut the thread down
        if(this.getThread().isAlive()) {

            this.getThread().shutdown();

        } else {
            Cloud.getInstance().getLogger()
                    .debug("thread " + threadTag + " isn't alive");
        }

    }

    private void destroyForcibly() {

        /*
        if((this.process != null)
                && (!this.process.isAlive())) {
            this.process.destroyForcibly();
        }

         */

    }

    @Override
    public Process getProcess() {
        //return this.process;
        return null;
    }

    @Override
    public CloudThread getThread() {
        return Cloud.getInstance().getThreadManager().getThread(this.threadID);
    }

    public void createEnvironment() throws IOException {

        new CloudFolder(super.getPath());

        copyServerFile0();
        copyTemplateFiles0();
        this.createServerProperties0();

        /*
        this.createSpigotYML0();
        this.createBukkitYML0();

        this.copyTemplateFiles0();
        this.copyServerFile0();

         */

    }

    private void createServerProperties0() throws IOException {

        final String path = super.getPath() + "//server.properties";

        if(Files.notExists(Paths.get(path))) {
            Files.copy(Objects.requireNonNull(Server.class.getClassLoader().getResourceAsStream("server.properties")), Paths.get(path));
        }

        final File propertiesFile = new File(path);
        /*
        final FileWriter writer = new FileWriter(propertiesFile);

        writer.write("server-port=" + super.getPort() + "\n");
        writer.write("motd=" + super.getMotd() + "\n");
        writer.write("max-players=" + super.getMaxPlayers() + "\n");
        writer.write("server-name=" + super.getDisplay() + "\n");

        writer.close();
         */

        Utils.addFileLine(propertiesFile, "\nserver-port=" + super.getPort());
        Utils.addFileLine(propertiesFile, "motd=" + super.getMotd());
        Utils.addFileLine(propertiesFile, "max-players=" + super.getMaxPlayers());
        Utils.addFileLine(propertiesFile, "server-name=" + super.getDisplay());

    }


    public void createServerProperties() {

        try {
            this.createServerProperties0();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void createSpigotYML0() throws IOException {

        final String path = super.getPath() + "//spigot.yml";

        if(!Files.notExists(Paths.get(path))) {
            Files.copy(Objects.requireNonNull(Server.class.getClassLoader().getResourceAsStream("spigot.yml")), Paths.get(path));
        }

    }

    public void createSpigotYML() {

        try {
            this.createSpigotYML0();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void createBukkitYML0() throws IOException {

        final String path = super.getPath() + "//bukkit.yml";

        if(!Files.notExists(Paths.get(path))) {
            Files.copy(Objects.requireNonNull(Server.class.getClassLoader().getResourceAsStream("bukkit.yml")), Paths.get(path));
        }

    }

    public void createBukkitYML() {

        try {
            this.createBukkitYML0();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void copyServerFile() {
        try {
            this.copyServerFile0();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void copyServerFile0() throws IOException {

        Files.copy(Path.of("local//lib//server.jar"), Path.of(super.getPath() + "//server.jar"), StandardCopyOption.REPLACE_EXISTING);

        /*
        CloudFile source = new CloudFile("local//lib", "server.jar");
        CloudFolder destDir = new CloudFolder(super.getPath());

        if(source.get().isFile()) {

            JarFile file = new JarFile(source.getPath());
            Copier.copyJarFile(file, destDir.get());

            return;
        }

        if(source.get().isDirectory()) {

            File[] files = source.get().listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jar");
                }
            });
            for (File f : files) {
                Cloud.getInstance().getLogger().debug("Copying server.jar...");
                Copier.copyJarFile(new JarFile(f), destDir.get());
                Cloud.getInstance().getLogger().debug("Copying server.jar finished!");
            }

        }

        //Files.copy(Path.of(""), Path.of(""), StandardCopyOption.REPLACE_EXISTING);

         */

    }

    public void copyTemplateFiles() {
        try {
            this.copyTemplateFiles0();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void copyTemplateFiles0() throws IOException {
        final CloudFolder from =
                new CloudFolder("local//templates//" + super.getGroup().getTemplate().getName());

        final CloudFolder to =
                new CloudFolder(super.getPath());

        super.getGroup().getTemplate()
                .copyFromTo(from.get(), to.get());
    }

}
