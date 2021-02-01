package de.z1up.supercloud.cloud.server.mngmt;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.Server;
import de.z1up.supercloud.cloud.server.Template;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.file.Copier;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.jar.JarFile;

public class ServerManager ***REMOVED***

    private final String PATH = "local//lib//";
    private final String PROXY_FILE = "proxy.jar";
    private final String SERVER_FILE = "server.jar";

    public void startServer(Server server) ***REMOVED***

        /*
        File dir = new File(server.getPath());

        if(!dir.exists()) ***REMOVED***
            try ***REMOVED***
                createServerEnvironment(server);
            ***REMOVED*** catch (IOException exception) ***REMOVED***
                exception.printStackTrace();
            ***REMOVED***
        ***REMOVED***

        try ***REMOVED***



            /*
            String path = Paths.get(".").toAbsolutePath().normalize().toString();
            Cloud.getInstance().getLogger().log(path);
            Cloud.getInstance().getLogger().log(server.getPath());

            String cd_cmd = "cd " + path + "//" + server.getPath() + "";
            //Runtime.getRuntime().exec("cd \"local\"");

            Process p = Runtime.getRuntime().exec("cmd.exe /k ping google.com");
            InputStream in = p.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";

            while ((line = bufferedReader.readLine()) != null) ***REMOVED***
                System.out.println(line);
            ***REMOVED***

            System.out.println("Starting wrapper process...");
            this.process = new ProcessBuilder("java",
                    "-Xmx256M",
                    "-Djline.terminal=jline.UnsupportedTerminal",
                    "-Dcloudnet.logging.prompt.disabled=true",
                    "-jar",
                    "CloudNet-Wrapper.jar").directory(new File("wrapper")).start();
            this.initConsoleThread();
            System.out.println("Successfully started the wrapper process!");

            /*
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(new File(server.getPath()));
            //builder.command("cmd.exe", "java -jar server.jar -o true -eula=true");
            builder.command("cmd.exe /k ping google.com");
            //builder.command("java -jar server.jar -o true -eula=true");
            Process process = builder.start();

            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";

            while ((line = bufferedReader.readLine()) != null) ***REMOVED***
                System.out.println(line);
            ***REMOVED***

            //Runtime.getRuntime().exec("( cd " + server.getPath() + " && java -jar " + SERVER_FILE + " - o true )");

            /*
            String cmd = "( cd \"" + server.getPath() + "//\" && java -jar " + SERVER_FILE + " )";

            Cloud.getInstance().getLogger().log(cmd + " ----- " + Runtime.getRuntime().exec(new String[]***REMOVED***"cd \"" + server.getPath() + "//\""***REMOVED***));
            Process process = Runtime.getRuntime().exec(cmd);
            InputStream in = process.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";

            while ((line = bufferedReader.readLine()) != null) ***REMOVED***
                System.out.println(line);
            ***REMOVED***
             */

    ***REMOVED***


    public void createServerEnvironment(Server server) throws IOException ***REMOVED***

        Cloud.getInstance().getLogger().debug("Creating server environment for " + server.getDisplay() + "...");

        String path = server.getPath();

        Template template = server.getGroup().getTemplate();

        File sourceFileOrDir = new File(PATH + SERVER_FILE);
        File destDir = new File(path);
        destDir.mkdirs();
        if (sourceFileOrDir.isFile()) ***REMOVED***
            Copier.copyJarFile(new JarFile(sourceFileOrDir), destDir);
        ***REMOVED*** else if (sourceFileOrDir.isDirectory()) ***REMOVED***
            File[] files = sourceFileOrDir.listFiles(new FilenameFilter() ***REMOVED***
                public boolean accept(File dir, String name) ***REMOVED***
                    return name.endsWith(".jar");
                ***REMOVED***
            ***REMOVED***);
            for (File f : files) ***REMOVED***
                Cloud.getInstance().getLogger().debug("Copying server.jar...");
                Copier.copyJarFile(new JarFile(f), destDir);
                Cloud.getInstance().getLogger().debug("Copying server.jar finished!");
            ***REMOVED***
        ***REMOVED***

        CloudFolder to = new CloudFolder(path);
        CloudFolder from = new CloudFolder("local//templates//" + template.getName());
        template.copyFromTo(from, to);

        Cloud.getInstance().getLogger().debug("Creating server environment for " + server.getDisplay() + " finished!");
    ***REMOVED***

***REMOVED***
