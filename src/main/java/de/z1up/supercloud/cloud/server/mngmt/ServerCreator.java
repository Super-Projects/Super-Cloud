package de.z1up.supercloud.cloud.server.mngmt;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.*;
import de.z1up.supercloud.cloud.server.enums.GroupMode;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.cloud.server.enums.ServerMode;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.id.UIDType;

import java.util.ArrayList;
import java.util.Collection;

public class ServerCreator {

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

        ProxyServer proxy = new ProxyServer(uid, serverType, serverMode, display, group, maintenance, id, path, false, 25565, 20, "A minecraft server");
        return proxy;
    }

}
