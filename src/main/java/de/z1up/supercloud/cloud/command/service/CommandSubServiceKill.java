package de.z1up.supercloud.cloud.command.service;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.cloud.server.obj.Server;
import org.bson.Document;

import java.io.IOException;

public class CommandSubServiceKill extends SubCommand {

    public CommandSubServiceKill(Command superCommand) {
        super("service_kill",
                "Kill an existing service",
                "service kill <Name>",
                null,
                superCommand);
    }

    @Override
    public boolean onExecute(String[] args) {

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

            Cloud.getInstance().getLogger().log("Error: Service is still connected! Please run 'service stop " + server.getDisplay() + "' first, in order to kill the service!");
            return true;
        }

        Cloud.getInstance().getLogger().log("Killing server '" + server.getDisplay() + "'!");
        server.destroy();


        return false;
    }

}
