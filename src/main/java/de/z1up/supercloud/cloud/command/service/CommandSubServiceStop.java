package de.z1up.supercloud.cloud.command.service;

import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.cloud.command.SubCommand;

public class CommandSubServiceStop extends SubCommand {

    public CommandSubServiceStop(Command superCommand) {
        super("service_stop", "Stop a connected service", "service stop <Service>", null, superCommand);
    }

    @Override
    public boolean onExecute(String[] args) {
        return false;
    }
}
