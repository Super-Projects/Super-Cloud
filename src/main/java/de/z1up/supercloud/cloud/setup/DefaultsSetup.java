package de.z1up.supercloud.cloud.setup;

import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.Group;
import de.z1up.supercloud.cloud.server.Server;
import de.z1up.supercloud.cloud.server.mngmt.ServerCreator;
import de.z1up.supercloud.core.file.CloudFolder;
import de.z1up.supercloud.core.input.InputReader;
import de.z1up.supercloud.core.interfaces.SetUp;

import java.io.File;

public class DefaultsSetup implements SetUp {

    private final String PATH             = "local//groups";
    private final ServerCreator creator   = new ServerCreator();

    private boolean completed;

    @Override
    public void runSetUp() {

        checkCompleted();

        if(isCompleted()) {
            return;
        }

        askForDefaultProxy();
        askForDefaultLobby();

    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public void checkCompleted() {

        File dir = new File(this.PATH);

        if(dir.exists()) {
            this.completed = true;
        } else {
            this.completed = false;
        }

    }

    private void askForDefaultProxy() {

        Cloud.getInstance().getLogger()
                .log("Do want me to create a default proxy for you? (§aY§7/§cN§7)");

        InputReader reader = new InputReader();
        reader.open();

        String input = reader.getInput();

        if(input.equalsIgnoreCase("Y")) {

            Cloud.getInstance().getLogger()
                    .log("§aGreat! I'll create a new proxy group for you...");

            creator.createDefaultProxyGroup();

        }
    }

    private void askForDefaultLobby() {

        Cloud.getInstance().getLogger()
                .log("Do want me to create a default lobby for you? (§aY§7/§cN§7)");

        InputReader reader = new InputReader();
        reader.open();

        String input = reader.getInput();

        if(input.equalsIgnoreCase("Y")) {

            Cloud.getInstance().getLogger()
                    .log("§aGreat! I'll create a new server group for you...");

            this.creator.createDefaultLobbyGroup();

            Group lobbyGroup = Cloud.getInstance().getGroupManager().getGroupByName("lobby");

            Server lobby = Cloud.getInstance().getServerCreator().createServerByGroup(lobbyGroup);
            lobby.save();

        }
    }
}
