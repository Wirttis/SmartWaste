package com.example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import java.util.ArrayList;

public class MongoListener {
    MongoDatabase database;
    MongoCollection<Document> collectionM;
    MongoCollection<Document> collectionC;

    public MongoListener(MongoHandler mongoHandler) {
        database = mongoHandler.getDatabase();
        collectionM = database.getCollection("Measurements");
        collectionC = database.getCollection("Containers");
    }

    public ArrayList<Document> getContainers() {
        ArrayList<Document> containers = new ArrayList<>();
        FindIterable<Document> docs = collectionC.find();
        docs.forEach(containers::add);
        return containers;
    }

    public ArrayList<Document> getLatestContainers() {
        ArrayList<Document> measurements  = new ArrayList<>();
        FindIterable<Document> docs = collectionC.find();
        docs.forEach(document -> measurements.add(collectionM.find().filter(Filters.eq("bin_id",document.get("location_id"))).sort(Sorts.descending("created_at")).first()));
        return measurements;
    }
}
