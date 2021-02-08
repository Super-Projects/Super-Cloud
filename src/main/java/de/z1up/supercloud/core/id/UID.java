package de.z1up.supercloud.core.id;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import de.z1up.supercloud.cloud.Cloud;
import org.bson.Document;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class UID extends StringGenerator {

    private String tag;
    private UIDType type;

    public UID(String tag, UIDType type) {
        this.tag = tag;
        this.type = type;
    }

    public static UID randomUID(UIDType type) {
        String tag = generateRandomTag(16);

        if(UID.exists(tag)) {
            return randomUID(type);
        }

        return new UID(tag, type);
    }

    public String getTag() {
        return tag;
    }

    public UIDType getType() {
        return type;
    }

    public void setType(UIDType type) {
        this.type = type;
    }

    public static boolean exists(String tag) {


        final MongoIterable<String> collectionNames
                = Cloud.getInstance().getMongoManager().getDatabase().listCollectionNames();

        final AtomicBoolean exists
                = new AtomicBoolean(false);

        collectionNames.forEach((Consumer<? super String>) collectionName -> {

            final MongoCollection<Document> collection
                    = Cloud.getInstance().getMongoManager().getDatabase().getCollection(collectionName);
            if(existsTagInCollection(collection, tag)) {
                exists.set(true);
            }
        });

        return exists.get();
    }

    private static boolean existsTagInCollection(final MongoCollection<Document> collection, final String tag) {

        final Document query = new Document();
        query.append("uid.tag", tag);

        long count = collection.countDocuments(query);

        if(count != 0) {
            return true;
        }

        return false;
    }
}
