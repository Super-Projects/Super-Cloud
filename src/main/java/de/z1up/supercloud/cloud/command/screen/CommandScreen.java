package de.z1up.supercloud.cloud.command.screen;

import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;

public class CommandScreen extends Command {

    public CommandScreen() {
        super("screen",
                "Open and close screens for services",
                "screen",
                null,
                null);

        SubCommand[] subCommands = new SubCommand[]{
                new CommandSubScreenToggle(this),
        };
        super.setSubCommands(subCommands);
    }

    @Override
    public boolean onExecute(String[] args) {

        System.out.println("Syeasfsdfsdfsdfeaotjgiopejtgip");

        if (args.length < 2) {
            sendHelpMessage();
            return true;
        }

        String commandName = args[1];

        if(commandName.equalsIgnoreCase("toggle")) {
            super.getSubCommands()[0].onExecute(args);
            return true;
        }


        super.sendHelpMessage();


        return false;
    }
}
