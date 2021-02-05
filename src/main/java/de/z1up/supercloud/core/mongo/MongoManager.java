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

public class MongoManager {

    public static final Gson GSON = new Gson();

    private MongoClient client;
    private MongoDatabase database;
    private MongoConfiguration config;

    public void connect() {

        this.loadConfiguration();

        this.setLogging();

        this.buildClient();

        this.loadDatabase();

    }

    private void setLogging() {

        final boolean debug = Cloud.getInstance().getLogger().isDebugActive();

        if(debug) {
            ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ALL);
        } else {
            ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ALL);
        }

    }

    private synchronized void loadConfiguration() {

        final CloudFolder dir = new CloudFolder("mongo");
        dir.build();

        try {
            this.config = MongoConfiguration.fromFile("mongo//mongo.json");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void buildClient() {
        String con = this.config.buildClientURI().toString();
        this.client = MongoClients.create(con);
    }

    private void loadDatabase() {
        this.database = this.client.getDatabase(this.config.getDatabase());
    }

    public final MongoDatabase getDatabase() {
        return this.database;
    }

    public final MongoClient getClient() {
        return this.client;
    }

    public void disconnect() {
        if(this.client != null) {
            this.client.close();
        }
    }

    public boolean isConnected() {
        return (this.client != null ? true : false);
    }

}
