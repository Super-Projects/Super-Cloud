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

    public void killOldServer(long pid) {

        Optional<ProcessHandle> processHandle
                = ProcessHandle.of(pid);

        if(processHandle.isPresent()) {
            processHandle.get().destroyForcibly();
        } else {
            System.out.println("process not present");
        }

    }

    public void killOldServer(final Document document) {

        int pid = (int) document.get("pid");

        Optional<ProcessHandle> processHandle
                = ProcessHandle.of(29952);

        if(processHandle.isPresent()) {
            processHandle.get().destroyForcibly();
        } else {
            System.out.println("process not present");
        }

                /*
        if(processHandle == null) {
            return;
        }

        ProcessHandle process = processHandle.get();

        process.destroyForcibly();

        if((!processHandle.isEmpty()) || (!processHandle.isPresent())) {
            return;
        }

        return;
        /*
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

         */

    }

}
