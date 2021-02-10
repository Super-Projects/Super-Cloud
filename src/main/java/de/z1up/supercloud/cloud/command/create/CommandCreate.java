package de.z1up.supercloud.cloud.command.create;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;
import de.z1up.supercloud.core.time.CloudThread;

public class CommandCreate extends Command {

    public CommandCreate() {
        super("create",
                "Create new servers, proxys, etc...",
                "create",
                null,
                null);

        SubCommand[] subCommands = new SubCommand[]{
                new CommandSubCreateServer(this)
        };
        super.setSubCommands(subCommands);
    }

    @Override
    public boolean onExecute(String[] args) {

        if (args.length < 2) {
            sendHelpMessage();
            return true;
        }

        String commandName = args[1];

        if(commandName.equalsIgnoreCase("server")) {
            super.getSubCommands()[0].onExecute(args);
            return true;
        }
        sendHelpMessage();


        return false;
    }
}
