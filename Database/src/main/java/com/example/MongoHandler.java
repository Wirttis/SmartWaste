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
/*
public class MongoHandler {
    MongoDatabase database;

    public void connect() {
        //Connection to DB
        String connectionString = System.getenv("DATABASE_PASSWORD");


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
*/
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

public class MongoHandler {

    private MongoClient mongoClient;
    private MongoDatabase database;

    private final String connectionString = System.getenv("DATABASE_PASSWORD");

    public synchronized void connect() {
        int retries = 5;
        int delay = 2000;

        while (retries > 0) {
            try {
                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(connectionString))
                        .applyToClusterSettings(builder ->
                                builder.serverSelectionTimeout(5, java.util.concurrent.TimeUnit.SECONDS))
                        .build();

                mongoClient = MongoClients.create(settings);
                database = mongoClient.getDatabase("SmartWaste_DB");

                // test connection
                database.runCommand(new Document("ping", 1));

                System.out.println("Connected to MongoDB");
                return;

            } catch (Exception e) {
                System.out.println("Connection failed. Retrying... (" + retries + ")");
                retries--;

                try {
                    Thread.sleep(delay);
                    delay *= 2; // exponential backoff
                } catch (InterruptedException ignored) {}
            }
        }

        throw new RuntimeException("Failed to connect to MongoDB after retries");
    }

    public MongoDatabase getDatabase() {
        if (database == null) {
            connect();
        }

        try {
            // lightweight health check
            database.runCommand(new Document("ping", 1));
        } catch (Exception e) {
            System.out.println("Lost connection. Reconnecting...");
            reconnect();
        }

        return database;
    }

    private synchronized void reconnect() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        connect();
    }
}