package de.z1up.supercloud.core.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.z1up.supercloud.core.file.CloudFile;
import de.z1up.supercloud.core.file.CloudFolder;

import java.io.IOException;

public class MongoManager {

    public static final Gson GSON = new Gson();

    private MongoClient client;
    private MongoDatabase database;

    private void createFiles() {

        CloudFolder dir = new CloudFolder("mongo");
        dir.build();

    }

    public void connect() {

        createFiles();

        MongoConfig config = null;
        try {
            config = MongoConfig.load("mongo//mongo.json");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.client = MongoClients.create(new MongoClientURI(config.getURI()).toString());
        this.database = this.client.getDatabase(config.getDatabase());
        this.database.createCollection("test-collection");

    }

    public void disconnect() {
        if(this.client != null) {
            this.client.close();
        }
    }

}
