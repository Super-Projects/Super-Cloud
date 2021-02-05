package de.z1up.supercloud.cloud.server;

import com.google.gson.Gson;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.file.CloudFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class GroupManager ***REMOVED***

    private final String PATH = "local//groups";
    private Collection<Group> groups;

    public GroupManager() ***REMOVED***
    ***REMOVED***

    public List<Group> collectGroups() ***REMOVED***

        Cloud.getInstance().getLogger().debug("Starting to collect groups...");

        List<Group> collected = new ArrayList<>();

        CloudFolder dir = new CloudFolder(PATH);
        File[] files = dir.get().listFiles();

        for (File file : files) ***REMOVED***
            try ***REMOVED***

                Scanner scanner = new Scanner(file);
                String lines = "";
                while(scanner.hasNext())***REMOVED***
                    lines = lines + scanner.nextLine();
                ***REMOVED***

                Gson gson = new Gson();
                Object object = gson.fromJson(lines, Group.class);

                if(object instanceof Group) ***REMOVED***
                    collected.add((Group) object);
                    Cloud.getInstance().getLogger().debug("Collected group -> " + ((Group) object).getGroupName());
                ***REMOVED***

            ***REMOVED*** catch (FileNotFoundException e) ***REMOVED***
                e.printStackTrace();
            ***REMOVED***

        ***REMOVED***

        Cloud.getInstance().getLogger().debug("Collecting groups finished!");

        return collected;
    ***REMOVED***

    public void loadGroups() ***REMOVED***
        this.groups = collectGroups();
    ***REMOVED***

    public Group getGroupByName(String name) ***REMOVED***

        Cloud.getInstance().getLogger().debug("Searching for group \"" + name + "\"...");

        Group target = null;

        for (Group group : groups) ***REMOVED***

            if(group.getGroupName().toUpperCase().equals(name.toUpperCase())) ***REMOVED***
                target = group;
                Cloud.getInstance().getLogger().debug("Found group \"" + name + "\"...");
            ***REMOVED***

        ***REMOVED***

        Cloud.getInstance().getLogger().debug("Search for group \"" + name + "\" finished!");

        return target;
    ***REMOVED***

    public boolean isRegistered(Group group) ***REMOVED***
        return groups.contains(group);
    ***REMOVED***
***REMOVED***
