package com.gradle.SmartWaste;

import com.example.MongoListener;
import com.gradle.MQTT.MessageInterface;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlertController implements MessageInterface {
    ArrayList<Document> containers;
    Map<Document, Boolean> alertFlags;
    MongoListener mongoListener = new MongoListener(Main.mongoHandler);

    public AlertController() {
        containers = mongoListener.getContainers();
        alertFlags = new HashMap<>();
        for (Document container : containers) alertFlags.put(container, false);
    }

    public void doAlert(Document container, Document measurement) {

        Document recipient = mongoListener.getContainerRecipient(container);
        Document location = mongoListener.getLocationById(container.getInteger("location_id"));
        // TODO create message
        String message = "";
        EmailSender.sendEmail(recipient.getString("email"), "Tyhjennystilaus", "This is a test: " + location.get("site_name") + "   fill: " + measurement.get("fill_level"));
    }

    public boolean getAlertFlag(Document document) {
        return alertFlags.get(document);
    }

    public void changeAlertFlag(Document document) {
        alertFlags.put(document, !alertFlags.get(document));
    }

    @Override
    public void handleMessage(String topic, String message) {
        try {
            Document document = Document.parse(message);
            Document container = mongoListener.getContainerById(document.getInteger("bin_id"));
            double weight = Double.parseDouble(document.get("weight").toString());
            double threshhold = Double.parseDouble(container.get("alert_threshold_kg").toString());

            if (getAlertFlag(container)&& weight <= threshhold) {
                changeAlertFlag(container);
            } else if (weight >= threshhold && !getAlertFlag(container)) {
                doAlert(container, document);
                changeAlertFlag(container);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
