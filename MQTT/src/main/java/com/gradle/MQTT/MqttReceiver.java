package com.gradle.MQTT;

import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;


/**
 * com.gradle.MQTT.MqttReceiver
 * receives sent data
 * Does not need to be within the same network as sender
 * TODO
 * forward sent data somewhere
 * make sure the data is correct
 * handshake maybe?
 */

public class MqttReceiver {

    private final MqttAsyncClient client;
    private ArrayList<String> topics = new ArrayList<>();
    private MessageInterface messageHandler = null;

    public MqttReceiver(MessageInterface messageHandler) throws MqttException {
        this.messageHandler = messageHandler;
        String clientId = "receiver-" + System.currentTimeMillis();
        String broker = "ssl://8a5c3a59d6c946ffbe8bfaed051e8215.s1.eu.hivemq.cloud:8883";
        client = new MqttAsyncClient(broker, clientId, new MemoryPersistence());
    }
    public void start() throws MqttException {
        connect();
        subscribe("smartwaste/data");
        setCallback();
    }
    private MqttConnectOptions createConnectOptions() {
        String username = "Backend";
        String password = "e*tvY48I;M32m3)NNOq+uebvo6,k+f73";
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        return options;
    }
    public void connect() throws MqttException {
        MqttConnectOptions options = createConnectOptions();
        IMqttToken token = client.connect(options);
        token.waitForCompletion();
        System.out.println("Listening on topics: " + topics.toString());
    }
    public void subscribe(String topic) throws MqttException {
        client.subscribe(topic, 0).waitForCompletion();
        System.out.println("Listening on topic: " + topic);
        topics.add(topic);
    }
    private void setCallback() throws MqttException {
            client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String messageString = new String(message.getPayload());
                messageHandler.handleMessage(topic, messageString);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {}
        });
    }
    public void stop() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            client.close();
            System.out.println("Disconnected from broker.");
        }
    }
}