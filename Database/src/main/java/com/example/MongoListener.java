package com.example;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

// TODO maybe rename function?
public class MongoListener {

    // database and collections
    MongoDatabase database;
    MongoCollection<Document> collectionM;
    MongoCollection<Document> collectionC;
    MongoCollection<Document> collectionL;

    // constructor
    public MongoListener(MongoHandler mongoHandler) {
        database = mongoHandler.getDatabase();
        collectionM = database.getCollection("Measurements");
        collectionC = database.getCollection("Containers");
        collectionL = database.getCollection("Locations");
    }

    // right now not used might have a use later
    // gets every container from Containers collection and adds them to an empty arraylist
    public ArrayList<Document> getContainers() {
        ArrayList<Document> containers = new ArrayList<>();
        FindIterable<Document> docs = collectionC.find();
        docs.forEach(containers::add);
        return containers;
    }

    // gets the latest measurement data from every container and returns them in an arraylist
    public ArrayList<Document> getLatestContainers() {
        ArrayList<Document> containerData = new ArrayList<>();
        FindIterable<Document> docs = collectionC.find();

        // iterates through every container
        docs.forEach(document -> {
            // finds latest measurement data from current container
            Document measurementDocument = collectionM.find().filter(Filters.eq("bin_id",document.get("location_id"))).sort(Sorts.descending("created_at")).first();
            // finds location data from current container
            Document locationData = collectionL.find().filter(Filters.eq("_id",document.get("location_id"))).first();

            // appends data to a new document and adds it to an arraylist
            if (measurementDocument != null && locationData != null) {
                Document doc = new Document();
                doc.append("id", document.get("location_id").toString())
                    .append("name", document.get("name").toString())
                    .append("location", locationData.get("site_name").toString())
                    .append("fillPercentage", measurementDocument.get("fill_level"))
                    .append("lastUpdated", measurementDocument.get("created_at").toString());
                containerData.add(doc);
            }
            else  containerData.add(null);
        });
        // returns the arraylist
        return containerData;
    }

    // method to add document to the database from frontend
    public void addContainerMeasurement(Document document){
        try {
            // add data to correct form
            double fill = Double.parseDouble(document.get("fillPercentage").toString());
            Document measurementDocument = new Document();
            measurementDocument.append("bin_id",Integer.parseInt(document.get("id").toString()))
                    .append("weight", (fill*0.01)*500)
                    .append("fill_level",document.get("fillPercentage"))
                    .append("created_at", LocalDateTime.now(ZoneId.of("UTC+2")));
            // add
            InsertOneResult result = collectionM.insertOne(measurementDocument);
            System.out.println("Inserted document id - insert many: " + result.getInsertedId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
