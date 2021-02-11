package de.z1up.supercloud.cloud.command;

import de.z1up.supercloud.cloud.command.clear.CommandClear;
import de.z1up.supercloud.cloud.command.create.CommandCreate;
import de.z1up.supercloud.cloud.command.exit.CommandExit;
import de.z1up.supercloud.cloud.command.help.CommandHelp;
import de.z1up.supercloud.cloud.command.service.CommandService;

public class SimpleCommandManager implements CommandManager{

    public Command[] commands;

    public SimpleCommandManager() {

    }

    @Override
    public void loadCommands() {

        this.commands = new Command[] {
                new CommandCreate(),
                new CommandHelp(),
                new CommandExit(),
                new CommandService(),
                new CommandClear(),
        };

    }

    @Override
    public Command getCommand(final String name) {

        Command command = null;

        for(final Command target : this.commands) {
            if(target.getName().equalsIgnoreCase(name)) command = target;
        }

        return command;
    }

    @Override
    public void executeCommand(Command command,
                               String[] args) {
        command.onExecute(args);
    }

    @Override
    public void executeCommand(String commandName,
                               String[] args) {
        this.executeCommand(this.getCommand(commandName), args);
    }

    @Override
    public Command[] getCommands() {
        return this.commands;
    }

    @Override
    public boolean existsCommand(final String name) {
        return this.getCommand(name) != null;
    }

}
