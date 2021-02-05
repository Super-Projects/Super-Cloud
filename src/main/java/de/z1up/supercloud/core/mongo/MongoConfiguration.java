package de.z1up.supercloud.core.mongo;

import com.mongodb.MongoClientURI;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class MongoConfiguration ***REMOVED***

    private final String host;
    private final String user;
    private final String database;
    private final String password;
    private final int port;

    private final boolean useSrv;

    private final boolean useAuth;
    private final String authDatabase;

    public static MongoConfiguration fromFile(String path) throws IOException ***REMOVED***
        if(Files.notExists(Paths.get(path))) ***REMOVED***
            Files.copy(Objects.requireNonNull(MongoConfiguration.class.getClassLoader().getResourceAsStream("mongo.json")), Paths.get(path));
        ***REMOVED***
        return MongoManager.GSON.fromJson(new FileReader(path), MongoConfiguration.class);
    ***REMOVED***

    public MongoConfiguration(String host, String user, String database, String password, int port, boolean useSrv, boolean useAuth, String authDatabase) ***REMOVED***
        this.host = host;
        this.user = user;
        this.database = database;
        this.password = password;
        this.port = port;
        this.useSrv = useSrv;
        this.useAuth = useAuth;
        this.authDatabase = authDatabase;
    ***REMOVED***

    public String getHost() ***REMOVED***
        return this.host;
    ***REMOVED***

    public String getUser() ***REMOVED***
        return this.user;
    ***REMOVED***

    public String getDatabase() ***REMOVED***
        return this.database;
    ***REMOVED***

    public String getPassword() ***REMOVED***
        return this.password;
    ***REMOVED***

    public int getPort() ***REMOVED***
        return this.port;
    ***REMOVED***

    public boolean useSrv() ***REMOVED***
        return this.useSrv;
    ***REMOVED***

    public boolean useAuth() ***REMOVED***
        return this.useAuth;
    ***REMOVED***

    public String getAuthDatabase() ***REMOVED***
        return this.authDatabase;
    ***REMOVED***

    public MongoClientURI buildClientURI() ***REMOVED***

        StringBuilder uri = new StringBuilder("mongodb");

        if(this.useSrv()) ***REMOVED***
            uri.append("+srv");
        ***REMOVED***

        uri.append("://");

        if(!this.useAuth()) ***REMOVED***
            uri.append(this.host + (!this.useSrv ? (":" + this.port) : ""));
            System.out.println(uri.toString());
            return new MongoClientURI(uri.toString());
        ***REMOVED***

        uri.append(this.user + ":" + this.password + "@" + this.host + (!this.useSrv ? (":" + this.port) : "") + "/" + this.authDatabase);
        System.out.println(uri.toString());
        return new MongoClientURI(uri.toString());
    ***REMOVED***
***REMOVED***
