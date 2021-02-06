package de.z1up.supercloud.cloud.server.group;

import com.google.gson.Gson;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.group.Group;
import de.z1up.supercloud.core.file.CloudFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class GroupManager {

    private final String PATH = "local//groups";
    private Collection<Group> groups;

    public GroupManager() {
    }

    public List<Group> collectGroups() {

        Cloud.getInstance().getLogger().debug("Starting to collect groups...");

        List<Group> collected = new ArrayList<>();

        CloudFolder dir = new CloudFolder(PATH);
        File[] files = dir.get().listFiles();

        for (File file : files) {
            try {

                Scanner scanner = new Scanner(file);
                String lines = "";
                while(scanner.hasNext()){
                    lines = lines + scanner.nextLine();
                }

                Gson gson = new Gson();
                Object object = gson.fromJson(lines, Group.class);

                if(object instanceof Group) {
                    collected.add((Group) object);
                    Cloud.getInstance().getLogger().debug("Collected group -> " + ((Group) object).getGroupName());
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        Cloud.getInstance().getLogger().debug("Collecting groups finished!");

        return collected;
    }

    public void loadGroups() {
        this.groups = collectGroups();
    }

    public Group getGroupByName(String name) {

        Cloud.getInstance().getLogger().debug("Searching for group \"" + name + "\"...");

        Group target = null;

        for (Group group : groups) {

            if(group.getGroupName().toUpperCase().equals(name.toUpperCase())) {
                target = group;
                Cloud.getInstance().getLogger().debug("Found group \"" + name + "\"...");
            }

        }

        Cloud.getInstance().getLogger().debug("Search for group \"" + name + "\" finished!");

        return target;
    }

    public boolean isRegistered(Group group) {
        return groups.contains(group);
    }
}
