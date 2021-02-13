package de.z1up.supercloud.cloud.command.service;

import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;

public class CommandService extends Command {

    public CommandService() {
        super("service",
                "Manage servers, proxys, etc...",
                "service",
                null,
                null);

        SubCommand[] subCommands = new SubCommand[]{
                new CommandSubServiceList(this),
                new CommandSubServiceStart(this),
                new CommandSubServiceKill(this),
                new CommandSubServiceStop(this),
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

        if(commandName.equalsIgnoreCase("list")) {
            super.getSubCommands()[0].onExecute(args);
            return true;
        }

        if(commandName.equalsIgnoreCase("start")) {
            super.getSubCommands()[1].onExecute(args);
            return true;
        }

        if(commandName.equalsIgnoreCase("kill")) {
            super.getSubCommands()[2].onExecute(args);
            return true;
        }

        if(commandName.equalsIgnoreCase("stop")) {
            super.getSubCommands()[3].onExecute(args);
            return true;
        }

        super.sendHelpMessage();


        return false;
    }

}
