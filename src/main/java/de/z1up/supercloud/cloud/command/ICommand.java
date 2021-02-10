package de.z1up.supercloud.cloud.command;

import de.z1up.supercloud.core.permissions.Permission;

public interface ICommand {

    String getName();

    String getDescription();

    String getUsage();

    Permission getPermission();

    boolean onExecute(String[] args);

}
