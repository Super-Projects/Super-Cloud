package de.z1up.supercloud.core.mongo;

import org.omg.CORBA.Object;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class MongoConfig {

    private final String host;
    private final String user;
    private final String database;
    private final String password;
    private final int port;


    public static MongoConfig load(String config) throws IOException {
        if(Files.notExists(Paths.get(config))) {
            Files.copy(Objects.requireNonNull(MongoConfig.class.getClassLoader().getResourceAsStream("mongo.json")), Paths.get(config));
        }
        return MongoManager.GSON.fromJson(new FileReader(config), MongoConfig.class);
    }

    public MongoConfig(String host, String user, String database, String password, int port) {
        this.host = host;
        this.user = user;
        this.database = database;
        this.password = password;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getDatabase() {
        return database;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public String getURI() {
        return "mongodb://" + this.user + ":" + this.password + "@" + this.host + ":" + this.port + "/" + this.database;
    }
}
