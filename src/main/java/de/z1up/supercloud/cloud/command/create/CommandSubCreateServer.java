package de.z1up.supercloud.cloud.command.create;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.cloud.server.obj.Server;

public class CommandSubCreateServer extends SubCommand {

    public CommandSubCreateServer(Command superCommand) {
        super("create_server", "Create a new Server by a group", "create server <group>", null, superCommand);
    }

    @Override
    public boolean onExecute(String[] args) {

        // server create group_name


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

        Server server = null;

        if(group.getGroupType() == GroupType.PROXY_GROUP) {

            server = Cloud.getInstance().getServerCreator()
                    .createProxyByGroup(group);
        } else  {

            server = Cloud.getInstance().getServerCreator()
                    .createServerByGroup(group);

        }

        if (server == null) {

            Cloud.getInstance().getLogger()
                    .log("§cError! Server couldn't be created!");

            return true;
        }

        server.save();

        Cloud.getInstance().getLogger().log("A new Server was created! Use command server '" + server.getDisplay()
                + "' to see mor information or use command 'start' to start the server!");

        return false;
    }
}
