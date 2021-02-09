package de.z1up.supercloud.core.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.Core;
import de.z1up.supercloud.core.Utils;
import de.z1up.supercloud.core.chat.Logger;
import de.z1up.supercloud.core.file.CloudFolder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UTFDataFormatException;
import java.sql.Time;

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
            ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.ERROR);
        } else {
            ((LoggerContext) LoggerFactory.getILoggerFactory()).getLogger("org.mongodb.driver").setLevel(Level.OFF);
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
        Cloud.getInstance().getLogger().log("Establishing a connection to " + this.config.getHost() + "...");
        final String URI = this.config.buildClientURI().toString();
        try {
            this.client = MongoClients.create(URI);
        } catch (MongoTimeoutException exception) {
            Utils.warningWrongDBAccessData();
            return;
        }

        if(client != null) Cloud.getInstance().getLogger().log("Connection successfully established! Database: CONNECTED");
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
