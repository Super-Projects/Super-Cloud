package de.z1up.supercloud.cloud.command.exit;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.command.Command;
import de.z1up.supercloud.core.thread.CloudRunnable;

import java.util.concurrent.TimeUnit;

public class CommandExit extends Command {

    public CommandExit() {
        super("exit", "Exit and stop the CloudSystem", "exit", null, null);
    }

    @Override
    public boolean onExecute(String[] args) {

        Cloud.getInstance().getLogger().log("Shutting CloudSystem down in 2 seconds!");

        new CloudRunnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        }.runTaskLater(TimeUnit.SECONDS, 2);

        return false;
    }
}
