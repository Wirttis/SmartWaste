package com.gradle.MQTT;

// interface for handling mqtt messages
public interface MessageInterface {
    void handleMessage(String topic, String message);
}
