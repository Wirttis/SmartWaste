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
        String broker = "tcp://broker.hivemq.com:1883";
        String clientId = "receiver-" + System.currentTimeMillis();
        String topic = "test/yourname/mqttdemo";

        MqttClient client = new MqttClient(broker, clientId);

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

        client.connect();
        client.subscribe(topic);

        System.out.println("Listening on topic: " + topic);
    }
}