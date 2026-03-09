package com.example;


import com.gradle.MQTT.MessageInterface;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class MessageHandler implements MessageInterface {
    MongoDatabase database;
    public MessageHandler(MongoHandler mongoHandler) {
        this.database = mongoHandler.getDatabase();
    }

    public void handleMessage(String topic, String message) {
        // print out received message and parse it to a mongodb document
        System.out.println("Received Message: " + message);
        Document document = Document.parse(message);

        // get the time and add it to the document,   this is a bad way of doing it and should be changed
        document.append("created_at", LocalDateTime.now(ZoneId.of("UTC+2")));

        // add the document  to Measurements collection in the database
        MongoCollection<Document> collection = database.getCollection("Measurements");
        try {
            InsertOneResult result = collection.insertOne(document);
            System.out.println("Inserted document id - insert many: " + result.getInsertedId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
