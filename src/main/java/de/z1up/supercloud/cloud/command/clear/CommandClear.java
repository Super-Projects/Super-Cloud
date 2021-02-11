package de.z1up.supercloud.cloud.command.clear;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;

public class CommandClear extends Command {

    public CommandClear() {
        super("clear", "Clear the console", "clear", null, null);
    }

    @Override
    public boolean onExecute(String[] args) {

        for(int i = 0; i < 100; i++) {
            System.out.println("\n");
        }

        Cloud.getInstance().getLogger().log("Console cleared!");

        return false;
    }
}
