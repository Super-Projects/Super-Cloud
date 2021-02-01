package de.z1up.supercloud.cloud.server;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.input.InputReader;
import de.z1up.supercloud.core.time.CloudRunnable;
import de.z1up.supercloud.core.time.CloudThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class GameServer extends Server {

    private Process process;

    public GameServer(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path, int port, int maxPlayers, String motd) {
        super(uid, serverType, serverMode, display, group, maintenance, id, path, port, maxPlayers, motd);
    }

    @Override
    public void startProcess() throws IOException {

        Cloud.getInstance().getLogger().debug("Starting new process for " + getDisplay() + "...");

        String command = "java " +
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

        Cloud.getInstance().getLogger().log("running command -> " + command);
        this.process = Runtime.getRuntime().exec(command, null, new CloudFolder(super.getPath()).get());

        new CloudThread() {
            @Override
            public void run() {
                InputStream in = process.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";

                while (true) {
                    try {
                        if (!((line = bufferedReader.readLine()) != null)) break;
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    System.out.println(line);
                }
            }
        }.start();

        new CloudRunnable() {
            @Override
            public void run() {

                InputReader reader = new InputReader();
                reader.open();

                String in = reader.getInput();

                if(in.equals("stop")) {

                    process.destroy();
                    try {
                        process.getOutputStream().close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    /*
                    try {
                        process.destroy();
                        //process.getOutputStream().write("stop".getBytes());
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }

                     */

                }

                reader.close();

            }
        }.runTaskLater(TimeUnit.SECONDS, 10);

    }

    @Override
    public void shutdown() {

    }

    @Override
    public Process getProcess() {
        return null;
    }

    @Override
    public Thread getThread() {
        return null;
    }

}
