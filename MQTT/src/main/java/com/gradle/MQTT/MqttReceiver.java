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
    private final MessageInterface messageHandler;
    private ArrayList<String> topics = new ArrayList<>();

    // constructor
    public MqttReceiver(MessageInterface messageHandler) throws MqttException {
        this.messageHandler = messageHandler;
        String clientId = "receiver-" + System.currentTimeMillis();
        // getting broker url from environmental variable
        String broker = System.getenv("MQTT_URL");
        // setting up mqtt client
        client = new MqttAsyncClient(broker, clientId, new MemoryPersistence());
    }

    // starting connection and listening
    public void start() throws MqttException {
        connect();
        subscribe("smartwaste/data");
        setCallback();
    }

    // connect options for safe connection
    private MqttConnectOptions createConnectOptions() {
        // getting  username and password from environmental variables
        String username = System.getenv("MQTT_USER");
        String password = System.getenv("MQTT_PASSWORD");
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        return options;
    }

    // mqtt connection
    public void connect() throws MqttException {
        MqttConnectOptions options = createConnectOptions();
        IMqttToken token = client.connect(options);
        token.waitForCompletion();
        System.out.println("Listening on topics: " + topics.toString());
    }

    // method to add subscriptions, right now only one tho
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

            // uses interface when message arrives
            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String messageString = new String(message.getPayload());
                messageHandler.handleMessage(topic, messageString);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }

    // should probably use this for graceful exit
    public void stop() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            client.close();
            System.out.println("Disconnected from broker.");
        }
    }
}