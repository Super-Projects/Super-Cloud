package de.z1up.supercloud.cloud.command;

public interface ISubCommand extends ICommand {

    Command getSuperCommand();

}
