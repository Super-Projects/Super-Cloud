package de.z1up.supercloud.cloud.server;

import de.z1up.supercloud.cloud.server.enums.GroupMode;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.core.id.UID;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class Group ***REMOVED***

    private final String PATH       = "local//groups";

    private String groupName;
    private String description;
    private Template template;
    private int minOnlineCount;
    private boolean maintenance;
    private GroupType groupType;
    private GroupMode groupMode;
    private UID uid;
    private Collection<Server> onlineServers;

    public Group(String groupName, String description, Template template, int minOnlineCount, boolean maintenance, GroupType groupType, GroupMode groupMode, UID uid, Collection<Server> onlineServers) ***REMOVED***
        this.groupName = groupName;
        this.description = description;
        this.template = template;
        this.minOnlineCount = minOnlineCount;
        this.maintenance = maintenance;
        this.groupType = groupType;
        this.groupMode = groupMode;
        this.uid = uid;
        this.onlineServers = onlineServers;
    ***REMOVED***

    public String getPath() ***REMOVED***
        return PATH;
    ***REMOVED***

    public String getGroupName() ***REMOVED***
        return groupName;
    ***REMOVED***

    public void setGroupName(String groupName) ***REMOVED***
        this.groupName = groupName;
    ***REMOVED***

    public String getDescription() ***REMOVED***
        return description;
    ***REMOVED***

    public void setDescription(String description) ***REMOVED***
        this.description = description;
    ***REMOVED***

    public Template getTemplate() ***REMOVED***
        return template;
    ***REMOVED***

    public void setTemplate(Template template) ***REMOVED***
        this.template = template;
    ***REMOVED***

    public int getMinOnlineCount() ***REMOVED***
        return minOnlineCount;
    ***REMOVED***

    public void setMinOnlineCount(int minOnlineCount) ***REMOVED***
        this.minOnlineCount = minOnlineCount;
    ***REMOVED***

    public boolean isMaintenance() ***REMOVED***
        return maintenance;
    ***REMOVED***

    public void setMaintenance(boolean maintenance) ***REMOVED***
        this.maintenance = maintenance;
    ***REMOVED***

    public GroupType getGroupType() ***REMOVED***
        return groupType;
    ***REMOVED***

    public void setGroupType(GroupType groupType) ***REMOVED***
        this.groupType = groupType;
    ***REMOVED***

    public GroupMode getGroupMode() ***REMOVED***
        return groupMode;
    ***REMOVED***

    public void setGroupMode(GroupMode groupMode) ***REMOVED***
        this.groupMode = groupMode;
    ***REMOVED***

    public UID getUniqueID() ***REMOVED***
        return uid;
    ***REMOVED***

    public void setUniqueID(UID uid) ***REMOVED***
        this.uid = uid;
    ***REMOVED***

    public Collection<Server> getOnlineServers() ***REMOVED***
        return onlineServers;
    ***REMOVED***

    public void setOnlineServers(Collection<Server> onlineServers) ***REMOVED***
        this.onlineServers = onlineServers;
    ***REMOVED***

    public void save() ***REMOVED***

        File dir = new File(PATH);

        if(!dir.exists()) ***REMOVED***
            dir.mkdirs();
        ***REMOVED***

        File file = new File(PATH, groupName + ".json");

        if(!file.exists()) ***REMOVED***
            try ***REMOVED***
                file.createNewFile();
            ***REMOVED*** catch (IOException exception) ***REMOVED***
                exception.printStackTrace();
            ***REMOVED***
        ***REMOVED***

        try ***REMOVED***
            FileWriter writer = new FileWriter(file);

            JSONObject object = new JSONObject(this);
            writer.write(object.toString(4));

            writer.close();

        ***REMOVED*** catch (IOException exception) ***REMOVED***
            exception.printStackTrace();
        ***REMOVED***

    ***REMOVED***

***REMOVED***

