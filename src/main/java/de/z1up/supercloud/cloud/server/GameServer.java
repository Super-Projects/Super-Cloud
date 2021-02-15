package de.z1up.supercloud.cloud.server;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.core.Utils;
import de.z1up.supercloud.core.event.handle.Event;
import de.z1up.supercloud.core.event.events.server.ServerBootstrapEvent;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.interfaces.Sender;
import de.z1up.supercloud.core.screen.Screen;
import de.z1up.supercloud.core.screen.SimpleSender;
import de.z1up.supercloud.core.settings.Setting;
import de.z1up.supercloud.core.thread.CloudThread;

import java.io.*;
import java.nio.file.*;
import java.util.Objects;

public class GameServer extends Server {

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

                    System.out.println("Cloud thread activated");

                    try {

                        final Process process
                                = Runtime.getRuntime().exec(command, null,
                                new CloudFolder(GameServer.this.getPath()).get());

                        GameServer.super.setProcess(process);

                        GameServer.this.bootstrapAfter();

                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                }
            };

            super.setThread(thread);

        Cloud.getInstance().getThreadManager()
                .createThread(this.getThread());

        try {
            this.createEnvironment();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Event event = new ServerBootstrapEvent(this,
                ServerBootstrapEvent.Result.SUCCESS, false);
        Cloud.getInstance().getEventManager().callEvent(event);

    }

    private void bootstrapAfter() {

        System.out.println("Bootstrap after");

        this.setPid(this.getProcess().pid());

        this.setConnected(true);

        final Sender sender
                = new SimpleSender(this.getDisplay(), GameServer.class);

        final Screen screen = new Screen(this.getProcess().getInputStream(),
                sender, Setting.getBool("auto-screening"));
        screen.enableScreening();

        this.setScreen(screen);

        super.update();
    }

    @Override
    public void createEnvironment() throws IOException {

        new CloudFolder(super.getPath());

        this.copyServerFile0();
        this.copyTemplateFiles0();
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

        Utils.appendFileLine(propertiesFile, "\nserver-port=" + super.getPort());
        Utils.appendFileLine(propertiesFile, "motd=" + super.getMotd());
        Utils.appendFileLine(propertiesFile, "max-players=" + super.getMaxPlayers());
        Utils.appendFileLine(propertiesFile, "server-name=" + super.getDisplay());

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

        if(Files.notExists(Paths.get(path))) {
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

        if(Files.notExists(Paths.get(path))) {
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

        if(Files.notExists(Path.of(super.getPath() + "//server.jar"))) {
            Files.copy(Path.of("local//lib//server.jar"), Path.of(super.getPath() + "//server.jar"), StandardCopyOption.REPLACE_EXISTING);
        }

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
