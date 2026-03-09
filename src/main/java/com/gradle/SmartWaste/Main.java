package com.gradle.SmartWaste;
import com.example.MessageHandler;
import com.example.MongoHandler;
import com.gradle.MQTT.*;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

// main method, using Spring Boot
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class Main {
    static MongoHandler mongoHandler;
    public static void main(String[] args) throws MqttException {
        // starts everything including mqtt client, mongo client and spring boot app
        mongoHandler = new MongoHandler();
        mongoHandler.connect();
        MessageHandler messageHandler = new MessageHandler(mongoHandler);
        MqttReceiver mqttReceiver = new MqttReceiver(messageHandler);
        mqttReceiver.start();
        SpringApplication.run(Main.class, args);
    }
}
