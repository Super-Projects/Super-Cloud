package de.z1up.supercloud.cloud.command.service;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.cloud.server.obj.Server;


import java.util.List;

public class CommandSubServiceList extends SubCommand {

    public CommandSubServiceList(Command superCommand) {
        super("service_list",
                "List all the registered services for a group, etc...",
                "service list <Group>",
                null, superCommand);
    }

    @Override
    public boolean onExecute(String[] args) {

        if(!(args.length > 2)) {
            super.sendHelp();
            return true;
        }

        final String groupNameArgument = args[2];

        if(!Cloud.getInstance().getGroupManager()
                .existsGroupWithName(groupNameArgument)) {

            Cloud.getInstance().getLogger()
                    .log("§cError! This group doesn't exists!");

            return true;
        }

        final Group group = Cloud.getInstance()
                .getGroupManager().getGroupByName(groupNameArgument);

        List<Server> servers = null;

        if(group.getGroupType() != GroupType.PROXY_GROUP) {
            servers = Cloud.getInstance().getServerManager().getGroupServers(group);
        } else {
            servers = Cloud.getInstance().getServerManager().getGroupProxy(group);
        }

        if(servers == null) {
            Cloud.getInstance().getLogger().log("Error when collecting servers by group!");
            return true;
        }

        if(servers.isEmpty()) {
            Cloud.getInstance().getLogger().log("No servers found!");
            return true;
        }

        Cloud.getInstance().getLogger().log("Servers found for group '" + group.getGroupName() + "':\n");

        servers.forEach(server -> {
            Cloud.getInstance().getLogger().help(this.format(server));
        });

        Cloud.getInstance().getLogger().clearLine();

        return false;
    }

    private String format(final Server server) {
        return String.format("%15s | %10d | %10s | %20s | %-10s", server.getDisplay(), server.getPort(), server.getServerType(), (server.isConnected() ? "CONNECTED" : "DISCONNECTED"), server.getUniqueID().getTag());
    }
}