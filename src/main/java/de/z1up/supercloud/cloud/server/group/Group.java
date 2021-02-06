package de.z1up.supercloud.cloud.server.group;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.cloud.server.enums.GroupMode;
import de.z1up.supercloud.cloud.server.enums.GroupType;
import de.z1up.supercloud.cloud.server.obj.Server;
import de.z1up.supercloud.cloud.server.obj.Template;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.mongo.MongoUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Collection;

public class Group extends MongoUtils {

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

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("groups");

        Bson query = Filters
                .eq("uid.tag", this.uid.getTag());

        if(super.exists(collection, query)) {
            this.update0(collection);
        } else {

            final Document insert
                    = Document.parse(new Gson().toJson(this));

            super.insert(collection, insert);

        }

    }

    public void update() {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        final MongoCollection<Document> collection = database.getCollection("groups");

        this.update0(collection);

    }

    private void update0(final MongoCollection<Document> collection) {

        final Bson query
                = Filters.eq("uid.tag", this.uid.getTag());

        final Document insert
                = Document.parse(new Gson().toJson(this));

        super.updateDocument(collection, query, insert);

    }

}

