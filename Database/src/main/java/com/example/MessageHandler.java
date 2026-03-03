package com.example;


import com.gradle.MQTT.MessageInterface;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import java.time.LocalDateTime;

public class MessageHandler implements MessageInterface {
    MongoDatabase database;
    public MessageHandler(MongoHandler mongoHandler) {
        this.database = mongoHandler.getDatabase();
    }

    public void handleMessage(String topic, String message) {
        System.out.println("Received Message: " + message);
        Document document = Document.parse(message);
        document.append("created_at", LocalDateTime.now());
        MongoCollection<Document> collection = database.getCollection("Measurements");
        try {
            InsertOneResult result = collection.insertOne(document);
            System.out.println("Inserted document id - insert many: " + result.getInsertedId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
