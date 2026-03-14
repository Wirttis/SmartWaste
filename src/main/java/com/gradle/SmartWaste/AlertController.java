package com.gradle.SmartWaste;

import com.example.MongoListener;
import com.gradle.MQTT.MessageInterface;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlertController implements MessageInterface {
    ArrayList<Document> containers;
    Map<Document, Boolean> alertFlags ;
    MongoListener mongoListener = new MongoListener(Main.mongoHandler);
    public AlertController() {
        containers = mongoListener.getContainers();
        alertFlags = new HashMap<>();
        for (Document container : containers) alertFlags.put(container, false);
    }

    public void doAlert(Document document){
        //
    }
    public boolean getAlertFlag(Document document){
        return alertFlags.get(document);
    }
    public void changeAlertFlag(Document document){
        alertFlags.put(document,!alertFlags.get(document));
    }

    @Override
    public void handleMessage(String topic, String message) {
        Document document = Document.parse(message);
        Document container = mongoListener.getContainerById(document.getString("bin_id"));
        float weight = (float) document.get("weight");
        float threshhold = (float) container.get("alert_threshold_kg");
        if(getAlertFlag(container)&& weight >= threshhold) {
            doAlert(container);
        } else if (getAlertFlag(container)) {
            changeAlertFlag(container);
        } else if (weight >= threshhold) {
            doAlert(container);
            changeAlertFlag(container);
        }
    }
}
