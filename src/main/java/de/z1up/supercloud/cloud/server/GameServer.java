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

public class GameServer extends Server ***REMOVED***

    private Process process;

    public GameServer(UID uid, ServerType serverType, ServerMode serverMode, String display, Group group, boolean maintenance, int id, String path, int port, int maxPlayers, String motd) ***REMOVED***
        super(uid, serverType, serverMode, display, group, maintenance, id, path, port, maxPlayers, motd);
    ***REMOVED***

    @Override
    public void startProcess() throws IOException ***REMOVED***

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

        new CloudThread() ***REMOVED***
            @Override
            public void run() ***REMOVED***
                InputStream in = process.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";

                while (true) ***REMOVED***
                    try ***REMOVED***
                        if (!((line = bufferedReader.readLine()) != null)) break;
                    ***REMOVED*** catch (IOException exception) ***REMOVED***
                        exception.printStackTrace();
                    ***REMOVED***
                    System.out.println(line);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***.start();

        new CloudRunnable() ***REMOVED***
            @Override
            public void run() ***REMOVED***

                InputReader reader = new InputReader();
                reader.open();

                String in = reader.getInput();

                if(in.equals("stop")) ***REMOVED***

                    process.destroy();
                    try ***REMOVED***
                        process.getOutputStream().close();
                    ***REMOVED*** catch (IOException exception) ***REMOVED***
                        exception.printStackTrace();
                    ***REMOVED***
                    /*
                    try ***REMOVED***
                        process.destroy();
                        //process.getOutputStream().write("stop".getBytes());
                    ***REMOVED*** catch (IOException exception) ***REMOVED***
                        exception.printStackTrace();
                    ***REMOVED***

                     */

                ***REMOVED***

                reader.close();

            ***REMOVED***
        ***REMOVED***.runTaskLater(TimeUnit.SECONDS, 10);

    ***REMOVED***

    @Override
    public void shutdown() ***REMOVED***

    ***REMOVED***

    @Override
    public Process getProcess() ***REMOVED***
        return null;
    ***REMOVED***

    @Override
    public Thread getThread() ***REMOVED***
        return null;
    ***REMOVED***

***REMOVED***
