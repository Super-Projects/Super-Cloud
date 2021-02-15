package de.z1up.supercloud.cloud.server.mngmt;

import com.mongodb.client.MongoCollection;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.GameServer;
import de.z1up.supercloud.cloud.server.ProxyServer;
import de.z1up.supercloud.cloud.server.Server;
import de.z1up.supercloud.cloud.server.Template;
import de.z1up.supercloud.cloud.server.enums.GroupMode;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;
import de.z1up.supercloud.core.mongo.MongoUtils;
import org.bson.Document;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class ServerCreator extends MongoUtils {

    public void createDefaultProxyGroup() {

        String name = "Proxy";
        String desc = "The default proxy group created by the cloud.";

        Template template = new Template(name);
        template.create();

        int minOnlineCount = 1;

        boolean maintenance = false;

        GroupType groupType = GroupType.PROXY_GROUP;
        GroupMode groupMode = GroupMode.DYNAMIC_GROUP;

        UID uid = UID.randomUID(UIDType.GROUP);

        Collection<Server> onlineServers = new ArrayList<>();

        Group group = new Group(name, desc, template, minOnlineCount, maintenance, groupType, groupMode, uid, onlineServers);
        group.save();

    }

    public void createDefaultLobbyGroup() {

        String name = "Lobby";
        String desc = "The default Lobby group created by the cloud.";

        Template template = new Template(name);
        template.create();

        int minOnlineCount = 1;

        boolean maintenance = false;

        GroupType groupType = GroupType.LOBBY_GROUP;
        GroupMode groupMode = GroupMode.STATIC_GROUP;

        UID uid = UID.randomUID(UIDType.GROUP);

        Collection<Server> onlineServers = new ArrayList<>();

        Group group = new Group(name, desc, template, minOnlineCount, maintenance, groupType, groupMode, uid, onlineServers);
        group.save();

    }

    public GameServer createServerByGroup(Group group) {

        Cloud.getInstance().getLogger().debug("Starting to create new GameServer by group " + group.getGroupName() + "...");

        UID uid = UID.randomUID(UIDType.SERVER);

        GroupType groupType = group.getGroupType();
        ServerType serverType = ServerType.SERVER;
        if(groupType == GroupType.LOBBY_GROUP) {
            serverType = ServerType.LOBBY;
        }


        GroupMode groupMode = group.getGroupMode();
        ServerMode serverMode = ServerMode.DYNAMIC;
        if(groupMode == GroupMode.STATIC_GROUP) {
            serverMode = ServerMode.STATIC;
        }

        String groupName = group.getGroupName();
        int id = group.getOnlineServers().size() + 1;
        String display = groupName + "-" + id;

        boolean maintenance = false;

        if (group.isMaintenance()) {
            maintenance = true;
        }

        String path = "local//" + (serverMode == ServerMode.DYNAMIC ? "temp//" + groupName + "//" + uid.getTag() : "perm//" + groupName + "//" + display);

        GameServer server = new GameServer(uid, serverType, serverMode, display, group, maintenance, id, path, false, 25565, 20, "A Minecraft Server");
        Cloud.getInstance().getLogger().debug("Server creation finished!");
        return server;
    }

    public ProxyServer createProxyByGroup(Group group) {

        UID uid = UID.randomUID(UIDType.SERVER);

        ServerType serverType = ServerType.PROXY;

        GroupMode groupMode = group.getGroupMode();
        ServerMode serverMode = ServerMode.DYNAMIC;
        if(groupMode == GroupMode.STATIC_GROUP) {
            serverMode = ServerMode.STATIC;
        }

        String groupName = group.getGroupName();
        int id = group.getOnlineServers().size() + 1;
        String display = groupName + "-" + id;

        boolean maintenance = false;

        if (group.isMaintenance()) {
            maintenance = true;
        }

        String path = "local//" + (serverMode == ServerMode.DYNAMIC ? "temp//" + groupName + "//" + uid.getTag() : "perm//" + groupName + "//" + display);

        int port = this.getRandomPort();

        ProxyServer proxy = new ProxyServer(uid, serverType, serverMode, display, group, maintenance, id, path, false, port, 20, "Server: " + display + " at port " + port);
        return proxy;
    }

    public int getRandomPort() {

        Random random = new Random();
        int port = random.nextInt(48127);
        port = port + 1024;

        if(port == 25565) {
            return this.getRandomPort();
        }

        /*
        if(this.portInUse(port)) {
            return this.getRandomPort();
        }
         */

        return port;
    }

    /**
     * Checks to see if a specific port is available.
     *
     * @param port the port to check for availability
     *
     * @implNote implemented from http://svn.apache.org/viewvc/camel/trunk/components/camel-test/src/main/java/org/apache/camel/test/AvailablePortFinder.java?view=markup#l130
     */
    private boolean portInUse(int port) {
        if (port < 1024 || port > 49151) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

    public int getRandomServerID() {

        final Random random = new Random();
        int id = random.nextInt(9999);

        final MongoCollection<Document> collection
                = Cloud.getInstance().getMongoManager().getDatabase().getCollection("servers");

        final Document query = new Document();
        query.append("id", id);


        if(super.exists(collection, query)) {
            return this.getRandomPort();
        }

        return id;
    }

}
