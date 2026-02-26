import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.*;

/**
 * MqttReceiver
 * receives sent data
 * Does not need to be within the same network as sender
 * TODO
 * forward sent data somewhere
 * make sure the data is correct
 * handshake maybe?
 */

public class MqttReceiver {

    public static void main(String[] args) throws Exception {

        //TODO
        String broker = "ssl://8a5c3a59d6c946ffbe8bfaed051e8215.s1.eu.hivemq.cloud:8883";
        String clientId = "receiver-" + System.currentTimeMillis();
        String topic = "smartwaste/data";
        String username = "Backend";
        String password = "e*tvY48I;M32m3)NNOq+uebvo6,k+f73";

        try (MqttClient client = new MqttClient(broker, clientId)){

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(username);
            options.setPassword(password.toCharArray());

            options.setAutomaticReconnect(true);
            options.setConnectionTimeout(10);

            client.connect(options);
            client.subscribe(topic);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println("Received: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {}
        });
        }
        catch (MqttException e) {
            System.err.println("MQTT Error");
            System.err.println("Reason Code: " + e.getReasonCode());
            System.err.println("Message: " + e.getMessage());
        }
        System.out.println("Listening on topic: " + topic);
    }
}