package com.example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class MongoListener {
    MongoDatabase database;
    MongoCollection<Document> collectionM;
    MongoCollection<Document> collectionC;
    MongoCollection<Document> collectionL;

    public MongoListener(MongoHandler mongoHandler) {
        database = mongoHandler.getDatabase();
        collectionM = database.getCollection("Measurements");
        collectionC = database.getCollection("Containers");
        collectionL = database.getCollection("Locations");
    }

    private String formatLastUpdated(Object createdAt) {
        if (createdAt == null) {
            return null;
        }
        if (createdAt instanceof Date) {
            Instant instant = ((Date) createdAt).toInstant();
            return DateTimeFormatter.ISO_INSTANT.format(instant);
        }
        return createdAt.toString();
    }

    public ArrayList<Document> getContainers() {
        ArrayList<Document> containers = new ArrayList<>();
        FindIterable<Document> docs = collectionC.find();
        docs.forEach(containers::add);
        return containers;
    }

    public ArrayList<Document> getLatestContainers() {
        ArrayList<Document> containerData = new ArrayList<>();
        FindIterable<Document> docs = collectionC.find();
        docs.forEach(document -> {
            Document measurementDocument = collectionM.find().filter(Filters.eq("bin_id",document.get("location_id"))).sort(Sorts.descending("created_at")).first();
            Document locationData = collectionL.find().filter(Filters.eq("_id",document.get("location_id"))).first();
            if (measurementDocument != null && locationData != null) {
                Document doc = new Document();
                doc.append("id", document.get("location_id").toString())
                    .append("name", document.get("name").toString())
                    .append("location", locationData.get("address").toString())
                    .append("fillPercentage", measurementDocument.get("fill_level"))
                    .append("lastUpdated", formatLastUpdated(measurementDocument.get("created_at")));
                containerData.add(doc);
            }
            else  containerData.add(null);
        });
        return containerData;
    }
}
