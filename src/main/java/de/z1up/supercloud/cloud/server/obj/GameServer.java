package de.z1up.supercloud.cloud.server.obj;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.screen.Screen;
import de.z1up.supercloud.core.time.CloudThread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class GameServer extends Server {

    private Process process;
    private CloudThread thread;
    private Screen screen;

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

        this.thread = new CloudThread() {
            @Override
            public void run() {

                GameServer server = GameServer.this;

                try {
                    server.process
                            = Runtime.getRuntime().exec(command, null, new CloudFolder(server.getPath()).get());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                server.setProcessPid(server.process.pid());

                server.screen = new Screen(server.process.getInputStream(), server.getDisplay(), false);

            }
        };

        this.thread.start();
    }

    @Override
    public void shutdown() {

        // check if thread even exists
        if(this.thread == null) {
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


        // finally shut the thread down
        if(this.thread.isAlive()) {

            this.thread.shutdown();

        } else {
            Cloud.getInstance().getLogger()
                    .debug("thread " + threadTag + " isn't alive");
        }

    }

    private void destroyForcibly() {

        if((this.process != null)
                && (!this.process.isAlive())) {
            this.process.destroyForcibly();
        }

    }

    @Override
    public Process getProcess() {
        return this.process;
    }

    @Override
    public CloudThread getThread() {
        return thread;
    }

    public void createEnvironmentFiles() throws IOException {

        this.createServerProperties0();
        this.createSpigotYML0();
        this.createBukkitYML0();

    }

    private void createServerProperties0() throws IOException {

        final String path = super.getPath() + "//server.properties";

        if(!Files.notExists(Paths.get(path))) {
            Files.copy(Objects.requireNonNull(Server.class.getClassLoader().getResourceAsStream("server.properties")), Paths.get(path));
        }

        final File propertiesFile = new File(path);
        final FileWriter writer = new FileWriter(propertiesFile);

        writer.write("server-port=" + super.getPort());
        writer.write("motd=" + super.getMotd());
        writer.write("max-players=" + super.getMaxPlayers());
        writer.write("server-name=" + super.getDisplay());

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

}
