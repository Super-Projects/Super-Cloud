package de.z1up.supercloud.cloud.command;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.permissions.Permission;

public abstract class SubCommand implements ISubCommand {

    private final String name;
    private final String description;
    private final String usage;
    private final Permission permission;
    private Command superCommand;

    public SubCommand(final String name,
                   final String description,
                   final String usage,
                   final Permission permission,
                   final Command superCommand) {

        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.superCommand = superCommand;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description == null ? "" : this.description;
    }

    @Override
    public String getUsage() {
        return this.usage == null ? "" : this.usage;
    }

    @Override
    public Permission getPermission() {
        return this.permission == null ? null : this.permission;
    }

    @Override
    public Command getSuperCommand() {
        return this.superCommand;
    }

    protected void sendHelp() {

        final String info = this.getUsage()
                + " | " + this.getDescription();
        Cloud.getInstance().getLogger()
                .help(info);

    }
}
