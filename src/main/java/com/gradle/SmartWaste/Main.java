package com.gradle.SmartWaste;
import com.example.MongoHandler;
import com.gradle.MQTT.*;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Main {
    public static void main(String[] args) throws MqttException {
        MessageHandler messageHandler = new MessageHandler();
        MqttReceiver mqttReceiver = new MqttReceiver(messageHandler);
        mqttReceiver.start();
        MongoHandler mongoHandler = new MongoHandler();
        mongoHandler.connect();
    }
}
