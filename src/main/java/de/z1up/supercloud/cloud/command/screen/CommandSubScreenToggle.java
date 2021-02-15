package de.z1up.supercloud.cloud.command.screen;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;
import de.z1up.supercloud.cloud.server.Server;
import de.z1up.supercloud.cloud.server.enums.ServerType;
import de.z1up.supercloud.core.screen.Screen;
import org.bson.Document;

public class CommandSubScreenToggle extends SubCommand {

    public CommandSubScreenToggle(Command superCommand) {
        super("screen_toggle",
                "Toggle the output for a service",
                "screen toggle <service>",
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
                    .log("§cError! This server doesn't exists!");

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
            Cloud.getInstance().getLogger().log("Error fetching server!");
            return true;
        }

        final Screen screen = server.getScreen();

        if(screen.isScreeningActive()) {
            screen.disableScreening();
        } else {
            screen.enableScreening();
        }

        server.setScreen(screen);
        server.save();

        Cloud.getInstance().getLogger()
                .log("Screening for server '" + server.getDisplay() + "' was "+ (screen.isScreeningActive() ? "enabled" : "disabled") + "!");

        return false;
    }
}
