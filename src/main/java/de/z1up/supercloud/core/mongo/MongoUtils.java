package de.z1up.supercloud.core.mongo;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import de.z1up.supercloud.core.interfaces.CloudObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class MongoUtils {

    protected void insert(final MongoCollection<Document> collection, final Document... documents) {

        if(documents.length > 1) {
            collection.insertMany(Arrays.asList(documents));
        } else {
            if(documents != null)
            collection.insertOne(documents[0]);
        }

    }

    protected void updateDocument(final MongoCollection<Document> collection, final Bson query, final Bson val) {

        final Document update = new Document();
        update.append("$set", val);

        collection.updateOne(
                query,
                update);

    }

    protected List<Document> selectDocuments(final MongoCollection<Document> collection) {

        final FindIterable<Document> iterable
                = collection.find();

        final List<Document> docs = new ArrayList<>();

        iterable.forEach((Consumer<? super Document>) doc -> docs.add(doc));

        return docs;
    }

    protected Document selectFirstDocument(final MongoCollection<Document> collection) {

        final FindIterable<Document> iterable
                = collection.find();

        final Document document
                = iterable.first();

        return document;

    }

    protected List<Document> selectDocuments(final MongoCollection<Document> collection, final Bson query) {

        final FindIterable<Document> iterable
                = collection.find(query);

        final List<Document> docs = new ArrayList<>();

        iterable.forEach((Consumer<? super Document>) doc -> docs.add(doc));

        return docs;
    }

    protected Document selectFirstDocument(final MongoCollection<Document> collection, final Bson query) {

        final FindIterable<Document> iterable
                = collection.find(query);

        final Document document
                = iterable.first();

        return document;
    }

    protected boolean exists(final MongoCollection collection, final Bson query) {

        List<Document> documents
                = this.selectDocuments(collection, query);

        if(!documents.isEmpty()) {
            return true;
        }

        return false;

    }

    protected DeleteResult delete(final MongoCollection collection, final Bson query) {
        DeleteResult result = collection.deleteMany(query);
        return result;
    }

    public Document parse(final Object object) {
        return Document.parse(new Gson().toJson(object));
    }

    public <T> T parse(final Document document, final Class<T> type) {
        return new Gson().fromJson(document.toJson(), type);
    }

}
