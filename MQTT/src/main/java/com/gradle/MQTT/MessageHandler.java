package com.gradle.MQTT;

public class MessageHandler {
    public void handleMessage(String topic, String message) {
        System.out.println("Topic: " + topic);
        System.out.println("Payload: " + message);
    }

}
