package com.gradle.SmartWaste;
import com.example.MessageHandler;
import com.example.MongoHandler;
import com.gradle.MQTT.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {
    static MongoHandler mongoHandler;
    public static void main(String[] args) throws MqttException {
        SpringApplication.run(Main.class, args);
        mongoHandler = new MongoHandler();
        mongoHandler.connect();
        MessageHandler messageHandler = new MessageHandler(mongoHandler);
        MqttReceiver mqttReceiver = new MqttReceiver(messageHandler);
        mqttReceiver.start();
    }
}
