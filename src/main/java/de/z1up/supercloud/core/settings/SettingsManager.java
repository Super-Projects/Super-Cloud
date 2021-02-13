package de.z1up.supercloud.core.settings;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.z1up.supercloud.cloud.Cloud;
import de.z1up.supercloud.core.mongo.MongoUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class SettingsManager extends MongoUtils {

    final MongoCollection<Document>     collection;

    public SettingsManager() {

        final MongoDatabase database = Cloud.getInstance().getMongoManager().getDatabase();
        this.collection = database.getCollection("settings");

        this.loadSettings();
    }

    public Setting getSetting(final String key) {
        final Document query = new Document();
        query.append("key", key);
        final Document document = super.selectFirstDocument(collection, query);
        return Setting.parse(document.toJson());
    }

    public boolean existsSetting(final String key) {
        final Document query = new Document();
        query.append("key", key);
        return super.exists(this.collection, query);
    }

    public void create(final Setting setting) {
        this.create(setting.getKey(), setting.getValue());
    }

    public void create(final String key, final Object value) {
        Setting setting = new Setting(key, value);
        super.insert(collection, super.parse(setting));
    }

    public void save(final Setting setting) {

        final Document query = new Document("key", setting.getKey());
        collection.updateOne(query, super.parse(setting));

    }

    public void load(final Setting setting) {

        if(!this.existsSetting(setting.getKey())) {
            this.create(setting);
        }

    }

    private final void loadSettings() {

        Setting.load("auto-screening", true);
        Setting.load("debugging", true);

    }

    public List<Setting> getSettings() {

        final List<Setting> settings
                = new ArrayList<>();

        super.selectDocuments(collection).forEach(document -> {
            Setting setting = super.parse(document, Setting.class);
        });

        return settings;
    }

}
