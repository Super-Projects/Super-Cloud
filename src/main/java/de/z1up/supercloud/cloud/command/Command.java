package de.z1up.supercloud.cloud.command;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.permissions.Permission;

public abstract class Command implements ICommand {

    private final String name;
    private final String description;
    private final String usage;
    private final Permission permission;
    private SubCommand[] subCommands;

    public Command(final String name,
                   final String description,
                   final String usage,
                   final Permission permission,
                   final SubCommand[] subCommands) {

        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.subCommands = subCommands;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description == null ?
                "" : this.description;
    }

    @Override
    public String getUsage() {
        return this.usage == null ?
                "This is the default usage!" : this.usage;
    }

    @Override
    public Permission getPermission() {
        return this.permission == null ?
                null : this.permission;
    }

    public SubCommand[] getSubCommands() {
        return this.subCommands == null ?
                new SubCommand[]{} : this.subCommands;
    }

    public void setSubCommands(SubCommand[] subCommands) {
        this.subCommands = subCommands;
    }

    public void sendHelpMessage() {

        Cloud.getInstance().getLogger().log("Help for '" + this.getName() + "':");

        Cloud.getInstance().getLogger()
                .help(this.getName() + " | " + this.getDescription());

        if(this.subCommands != null) {

            for(final SubCommand sub : this.subCommands) {

                final String info = sub.getUsage()
                        + " | " + sub.getDescription();
                Cloud.getInstance().getLogger()
                        .help(info);

            }

        }
    }
}
