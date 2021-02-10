package de.z1up.supercloud.core.input;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.CommandManager;
import de.z1up.supercloud.cloud.command.SimpleCommandManager;

public class CommandLine {

    private final InputReader           reader;
    private final CommandManager        commandManager;

    public CommandLine() {
        this.reader = new InputReader();
        this.commandManager = new SimpleCommandManager();
        this.load();
    }

    private void load() {
        this.commandManager.loadCommands();
    }

    public void open() {
        this.reader.open();
    }

    public void read() {

        this.open();

        final String readerInput
                = this.reader.getInput();

        final String input = readerInput;

        String[] args = input.split(" ");

        if(this.commandManager
                .existsCommand(args[0])) {

            Command command =
                    this.getCommandManager().getCommand(args[0]);

            this.commandManager.executeCommand(command, args);


        } else {

            Cloud.getInstance().getLogger()
                    .log("This command doesn't exists! Type 'help' for more information.");

        }

        this.read();
    }

    public void close() {
        this.reader.close();
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }
}
