package de.z1up.supercloud.cloud.server.group;

import com.mongodb.client.MongoCollection;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.id.UID;
import de.z1up.supercloud.core.mongo.MongoUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GroupManager extends MongoUtils {

    private Collection<Group> groups;

    public GroupManager() {
        this.groups = new ArrayList<>();
    }

    public Collection<Group> collectGroups() {

        final MongoCollection collection
                = Cloud.getInstance().getMongoManager().getDatabase().getCollection("groups");

        final List<Document> documents = super.selectDocuments(collection);

        final Collection<Group> collected = new ArrayList<>();

        documents.forEach(document -> {
            collected.add(Group.parse(document.toJson()));
        });

        return collected;
    }

    public void loadGroups() {
        this.groups = this.collectGroups();
    }

    public Group getGroupByName(final String name) {

        final MongoCollection collection
                = Cloud.getInstance().getMongoManager().getDatabase().getCollection("groups");

        final Bson query = new Document("groupName", name);

        final Document document =
                super.selectFirstDocument(collection, query);

        if(document == null) {
            return null;
        }

        final Group group
                = Group.parse(document.toJson());

        return group;
    }

    public Group getGroup(final String tag) {
        return this.getGroup0(tag);
    }

    public Group getGroup(final UID uid) {
        return this.getGroup0(uid.getTag());
    }

    private Group getGroup0(final String tag) {

        final MongoCollection collection
                = Cloud.getInstance().getMongoManager().getDatabase().getCollection("groups");

        final Document query = new Document();
        query.append("uid.tag", tag);

        final Document document =
                super.selectFirstDocument(collection, query);

        final Group group
                = Group.parse(document.toJson());

        return group;

    }

    public boolean isRegistered(Group group) {
        return groups.contains(group);
    }

    public boolean existsGroup(final String tag) {
        return this.existsGroup0(tag);
    }

    public boolean existsGroup(final UID uid) {
        return this.existsGroup0(uid.getTag());
    }

    private boolean existsGroup0(final String tag) {

        final MongoCollection collection
                = Cloud.getInstance().getMongoManager().getDatabase().getCollection("groups");

        final Document query = new Document();
        query.append("uid.tag", tag);

        return super.exists(collection, query);
    }

    public boolean existsGroupWithName(final String name) {
        return this.existsWithName0(name);
    }

    private boolean existsWithName0(final String name) {

        final MongoCollection collection
                = Cloud.getInstance().getMongoManager().getDatabase().getCollection("groups");

        final Document query = new Document();
        query.append("groupName", name);

        return super.exists(collection, query);
    }
}

