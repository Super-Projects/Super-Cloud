package de.z1up.supercloud.cloud.server;

import de.z1up.supercloud.cloud.server.enums.GroupMode;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.core.id.UID;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class Group {

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

    public Group(String groupName, String description, Template template, int minOnlineCount, boolean maintenance, GroupType groupType, GroupMode groupMode, UID uid, Collection<Server> onlineServers) {
        this.groupName = groupName;
        this.description = description;
        this.template = template;
        this.minOnlineCount = minOnlineCount;
        this.maintenance = maintenance;
        this.groupType = groupType;
        this.groupMode = groupMode;
        this.uid = uid;
        this.onlineServers = onlineServers;
    }

    public String getPath() {
        return PATH;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public int getMinOnlineCount() {
        return minOnlineCount;
    }

    public void setMinOnlineCount(int minOnlineCount) {
        this.minOnlineCount = minOnlineCount;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public GroupMode getGroupMode() {
        return groupMode;
    }

    public void setGroupMode(GroupMode groupMode) {
        this.groupMode = groupMode;
    }

    public UID getUniqueID() {
        return uid;
    }

    public void setUniqueID(UID uid) {
        this.uid = uid;
    }

    public Collection<Server> getOnlineServers() {
        return onlineServers;
    }

    public void setOnlineServers(Collection<Server> onlineServers) {
        this.onlineServers = onlineServers;
    }

    public void save() {

        File dir = new File(PATH);

        if(!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(PATH, groupName + ".json");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            FileWriter writer = new FileWriter(file);

            JSONObject object = new JSONObject(this);
            writer.write(object.toString(4));

            writer.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}

