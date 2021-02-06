package de.z1up.supercloud.cloud.server.mngmt;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.obj.Server;
import de.z1up.supercloud.cloud.server.obj.Template;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.file.Copier;
import de.z1up.supercloud.core.mongo.MongoUtils;
import org.bson.Document;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;

public class ServerManager extends MongoUtils {

    private final String PATH = "local//lib//";
    private final String PROXY_FILE = "proxy.jar";
    private final String SERVER_FILE = "server.jar";

    public void createServerEnvironment(Server server) throws IOException {

        Cloud.getInstance().getLogger().debug("Creating server environment for " + server.getDisplay() + "...");

        String path = server.getPath();

        Template template = server.getGroup().getTemplate();

        File sourceFileOrDir = new File(PATH + SERVER_FILE);
        File destDir = new File(path);
        destDir.mkdirs();
        if (sourceFileOrDir.isFile()) {
            Copier.copyJarFile(new JarFile(sourceFileOrDir), destDir);
        } else if (sourceFileOrDir.isDirectory()) {
            File[] files = sourceFileOrDir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jar");
                }
            });
            for (File f : files) {
                Cloud.getInstance().getLogger().debug("Copying server.jar...");
                Copier.copyJarFile(new JarFile(f), destDir);
                Cloud.getInstance().getLogger().debug("Copying server.jar finished!");
            }
        }

        CloudFolder to = new CloudFolder(path);
        CloudFolder from = new CloudFolder("local//templates//" + template.getName());
        template.copyFromTo(from, to);

        Cloud.getInstance().getLogger().debug("Creating server environment for " + server.getDisplay() + " finished!");
    }

    public void killOldServers() {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("servers");

        Document query = new Document();
        query.append("connected", true);

        List<Document> documents = selectDocuments(collection, query);

        if(documents.isEmpty()) {
            return;
        }

        documents.forEach(document -> {
            killOldServer(document);

            document.remove("connected");
            document.put("connected", false);

            super.updateDocument(collection, new Document("uid.tag", document.get("uid.tag")), document);
        });

    }

    private void killOldServer(final Document document) {

        long pid = (long) document.get("pid");

        Optional<ProcessHandle> processHandle
                = ProcessHandle.of(pid);

        if(processHandle == null) {
            return;
        }

        ProcessHandle process = processHandle.get();

        if(process == null) {
            return;
        }

        if(!process.isAlive()) {
            return;
        }

        try {
            process.wait(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        process.destroyForcibly();

    }

}
