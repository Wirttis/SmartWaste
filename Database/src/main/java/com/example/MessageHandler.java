package com.example;


import com.gradle.MQTT.MessageInterface;
import org.bson.json.JsonObject;

public class MessageHandler implements MessageInterface {
    @Override
    public void handleMessage(String topic, String message) {
        System.out.println("Received Message: " + message);
        JsonObject jsonObject = new JsonObject(message);
    }
}
