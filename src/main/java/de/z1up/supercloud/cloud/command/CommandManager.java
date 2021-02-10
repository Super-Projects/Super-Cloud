package de.z1up.supercloud.cloud.command;

public interface CommandManager {

    void loadCommands();

    Command getCommand(final String name);

    void executeCommand(final Command command, final String[] args);

    void executeCommand(final String commandName, final String[] args);

    Command[] getCommands();

    boolean existsCommand(final String name);

}
