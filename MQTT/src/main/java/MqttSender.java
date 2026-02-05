import org.eclipse.paho.client.mqttv3.*;


/**
 * Simulates data sending, currently sends integers, probably just a demo and poc
 * If you find any use for this please do tell or feel free to modify in anyway you see fit.
 * should work on separate device and does not need to be within the same network
 *
 *
 */


public class MqttSender {


    public static void main(String[] args) throws Exception {
        String broker = "tcp://broker.hivemq.com:1883";
        String clientId = "sender-" + System.currentTimeMillis();
        String topic = "test/yourname/mqttdemo";

        MqttClient client = new MqttClient(broker, clientId);
        client.connect();

        for (int i = 1; i <= 5; i++) {
            String payload = "Message #" + i + " from test PC";
            client.publish(topic, new MqttMessage(payload.getBytes()));
            System.out.println("Sent: " + payload);
            Thread.sleep(1000);
        }

        client.disconnect();
    }
}
