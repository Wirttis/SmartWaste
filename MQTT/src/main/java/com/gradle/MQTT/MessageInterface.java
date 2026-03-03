package com.gradle.MQTT;
public interface MessageInterface {
    void handleMessage(String topic, String message);
}
