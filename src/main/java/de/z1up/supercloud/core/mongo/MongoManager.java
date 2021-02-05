package de.z1up.supercloud.core.mongo;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.Core;
import de.z1up.supercloud.core.chat.Logger;
import de.z1up.supercloud.core.file.CloudFolder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MongoManager ***REMOVED***

    public static final Gson GSON = new Gson();

    private MongoClient client;
    private MongoDatabase database;
    private MongoConfiguration config;

    public void connect() ***REMOVED***

        this.loadConfiguration();

        this.setLogging();

        this.buildClient();

        this.loadDatabase();

    ***REMOVED***

    private void setLogging() ***REMOVED***

        final boolean debug = Cloud.getInstance().getLogger().isDebugActive();

        if(debug) ***REMOVED***
            ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ALL);
        ***REMOVED*** else ***REMOVED***
            ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ALL);
        ***REMOVED***

    ***REMOVED***

    private synchronized void loadConfiguration() ***REMOVED***

        final CloudFolder dir = new CloudFolder("mongo");
        dir.build();

        try ***REMOVED***
            this.config = MongoConfiguration.fromFile("mongo//mongo.json");
        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***

    ***REMOVED***

    private void buildClient() ***REMOVED***
        String con = this.config.buildClientURI().toString();
        this.client = MongoClients.create(con);
    ***REMOVED***

    private void loadDatabase() ***REMOVED***
        this.database = this.client.getDatabase(this.config.getDatabase());
    ***REMOVED***

    public final MongoDatabase getDatabase() ***REMOVED***
        return this.database;
    ***REMOVED***

    public final MongoClient getClient() ***REMOVED***
        return this.client;
    ***REMOVED***

    public void disconnect() ***REMOVED***
        if(this.client != null) ***REMOVED***
            this.client.close();
        ***REMOVED***
    ***REMOVED***

    public boolean isConnected() ***REMOVED***
        return (this.client != null ? true : false);
    ***REMOVED***

***REMOVED***
