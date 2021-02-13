package de.z1up.supercloud.cloud.command.setting;

import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;

public class CommandSettings extends Command {

    public CommandSettings() {
        super("settings",
                "Change the SystemSettings to you preferences",
                "settings <setting> <value>",
                null,
                null);

        SubCommand[] subCommands = new SubCommand[]{
                new CommandSubSettingsList(this),
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

        super.sendHelpMessage();
        return false;
    }
}
