package de.z1up.supercloud.core.mongo;

import org.omg.CORBA.Object;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class MongoConfig ***REMOVED***

    private final String host;
    private final String user;
    private final String database;
    private final String password;
    private final int port;


    public static MongoConfig load(String config) throws IOException ***REMOVED***
        if(Files.notExists(Paths.get(config))) ***REMOVED***
            Files.copy(Objects.requireNonNull(MongoConfig.class.getClassLoader().getResourceAsStream("mongo.json")), Paths.get(config));
        ***REMOVED***
        return MongoManager.GSON.fromJson(new FileReader(config), MongoConfig.class);
    ***REMOVED***

    public MongoConfig(String host, String user, String database, String password, int port) ***REMOVED***
        this.host = host;
        this.user = user;
        this.database = database;
        this.password = password;
        this.port = port;
    ***REMOVED***

    public String getHost() ***REMOVED***
        return host;
    ***REMOVED***

    public String getUser() ***REMOVED***
        return user;
    ***REMOVED***

    public String getDatabase() ***REMOVED***
        return database;
    ***REMOVED***

    public String getPassword() ***REMOVED***
        return password;
    ***REMOVED***

    public int getPort() ***REMOVED***
        return port;
    ***REMOVED***

    public String getURI() ***REMOVED***
        return "mongodb://" + this.user + ":" + this.password + "@" + this.host + ":" + this.port + "/" + this.database;
    ***REMOVED***
***REMOVED***
