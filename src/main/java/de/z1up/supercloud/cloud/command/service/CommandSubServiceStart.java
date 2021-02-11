package de.z1up.supercloud.cloud.command.service;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.cloud.server.obj.Server;
import org.bson.Document;

import java.io.IOException;
import java.util.List;

public class CommandSubServiceStart extends SubCommand {

    public CommandSubServiceStart(Command superCommand) {
        super("service_start",
                "Start an existing service",
                "service start <Name>",
                null, superCommand);
    }

    @Override
    public boolean onExecute(String[] args) {

        // server create group_name


        if(!(args.length > 2)) {
            super.sendHelp();
            return true;
        }

        final String serviceName = args[2];

        if(!Cloud.getInstance().getServerManager().existsService(serviceName)) {

            Cloud.getInstance().getLogger()
                    .log("§cError! This service doesn't exists!");

            return true;
        }

        final Document document = Cloud.getInstance().getServerManager().getAsDocument(serviceName);
        final ServerType serverType = Cloud.getInstance().getServerManager().getServerTypeFromDoc(document);

        Server server = null;

        if(serverType != ServerType.PROXY) {
            server = Cloud.getInstance().getServerManager().getFirstServer(serviceName);
        } else {
            server = Cloud.getInstance().getServerManager().getFirstProxy(serviceName);
        }

        if(server == null) {
            Cloud.getInstance().getLogger().log("Error fetching Server!");
            return true;
        }

        if(server.isConnected()) {
            Cloud.getInstance().getLogger().log("Server is already connected!");
            return true;
        }

        Cloud.getInstance().getLogger().log("Starting service...");

        try {
            server.bootstrap();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return false;
    }
}
