package de.z1up.supercloud.cloud.command.help;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.CommandManager;

public class CommandHelp extends Command {

    public CommandHelp() {
        super("help", "List of all executable commands", "help [<command>]", null, null);
    }

    @Override
    public boolean onExecute(String[] args) {

        final CommandManager manager =
                Cloud.getInstance().getCommandLine().getCommandManager();

        if(args.length > 1) {

            final String commandName
                    = args[1];

            if(Cloud.getInstance().getCommandLine()
                    .getCommandManager().existsCommand(commandName)) {

                final Command command = Cloud.getInstance()
                        .getCommandLine().getCommandManager().getCommand(commandName);
                command.sendHelpMessage();

                return true;
            } else {
                Cloud.getInstance().getLogger().log("Command couldn't be found!");
            }

        }

        Cloud.getInstance().getLogger().log("This might help you:\n");

        for (Command command : manager.getCommands()) {
            Cloud.getInstance().getLogger().help(command.getUsage() + " | " + command.getDescription());
        }

        Cloud.getInstance().getLogger().clearLine();


        return true;
    }
}
