package com.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoHandler {
    MongoDatabase database;

    public void connect() {
        //Connection to DB
        String connectionString = "mongodb+srv://SmartWasteUser:HsYSTT7CPFOURNH1@cluster0.ldvviwi.mongodb.net/?appName=Cluster0";

        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new client and connect to the server
        MongoClient mongoClient = MongoClients.create(settings);

        // Send a ping to confirm a successful connection
        database = mongoClient.getDatabase("SmartWaste_DB");

        database.runCommand(new Document("ping", 1));
        System.out.println("Pinged your deployment. You successfully connected to MongoDB!");


    }
    public MongoDatabase getDatabase() {
        return database;
    }
}
