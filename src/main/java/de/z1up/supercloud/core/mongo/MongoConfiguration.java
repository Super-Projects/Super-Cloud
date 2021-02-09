package de.z1up.supercloud.core.mongo;

import com.mongodb.MongoClientURI;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class MongoConfiguration {

    private final String host;
    private final String user;
    private final String database;
    private final String password;
    private final int port;

    private final boolean useSrv;

    private final boolean useAuth;
    private final String authDatabase;

    public static MongoConfiguration fromFile(String path) throws IOException {
        if(Files.notExists(Paths.get(path))) {
            Files.copy(Objects.requireNonNull(MongoConfiguration.class.getClassLoader().getResourceAsStream("mongo.json")), Paths.get(path));

            Utils.warningNoDBAccessData();

            System.exit(0);

            return null;
        }
        return MongoManager.GSON.fromJson(new FileReader(path), MongoConfiguration.class);
    }

    public MongoConfiguration(String host, String user, String database, String password, int port, boolean useSrv, boolean useAuth, String authDatabase) {
        this.host = host;
        this.user = user;
        this.database = database;
        this.password = password;
        this.port = port;
        this.useSrv = useSrv;
        this.useAuth = useAuth;
        this.authDatabase = authDatabase;
    }

    public String getHost() {
        return this.host;
    }

    public String getUser() {
        return this.user;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getPassword() {
        return this.password;
    }

    public int getPort() {
        return this.port;
    }

    public boolean useSrv() {
        return this.useSrv;
    }

    public boolean useAuth() {
        return this.useAuth;
    }

    public String getAuthDatabase() {
        return this.authDatabase;
    }

    public MongoClientURI buildClientURI() {

        StringBuilder uri = new StringBuilder("mongodb");

        if(this.useSrv()) {
            uri.append("+srv");
        }

        uri.append("://");

        if(!this.useAuth()) {
            uri.append(this.host + (!this.useSrv ? (":" + this.port) : ""));
            System.out.println(uri.toString());
            return new MongoClientURI(uri.toString());
        }

        uri.append(this.user + ":" + this.password + "@" + this.host + (!this.useSrv ? (":" + this.port) : "") + "/" + this.authDatabase);
        return new MongoClientURI(uri.toString());
    }
}
