package de.z1up.supercloud.core.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.z1up.supercloud.core.file.CloudFile;
import de.z1up.supercloud.core.file.CloudFolder;

import java.io.IOException;

public class MongoManager ***REMOVED***

    public static final Gson GSON = new Gson();

    private MongoClient client;
    private MongoDatabase database;

    private void createFiles() ***REMOVED***

        CloudFolder dir = new CloudFolder("mongo");
        CloudFile file = new CloudFile(dir, "access.yml");

    ***REMOVED***

    public void connect() ***REMOVED***

        createFiles();

        MongoConfig config = null;
        try ***REMOVED***
            config = MongoConfig.load("mongo//mongo.json");
        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***

        this.client = MongoClients.create(new MongoClientURI(config.getURI()).toString());
        this.database = this.client.getDatabase(config.getDatabase());
        this.database.createCollection("test-collection");

    ***REMOVED***

    public void disconnect() ***REMOVED***
        if(this.client != null) ***REMOVED***
            this.client.close();
        ***REMOVED***
    ***REMOVED***

***REMOVED***
