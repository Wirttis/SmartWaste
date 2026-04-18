package com.gradle.SmartWaste;

import com.example.MongoListener;
import com.gradle.MQTT.MessageInterface;
import org.bson.Document;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContainerService {
    private List<Container> containers = new ArrayList<>();
    MongoListener mongoListener = new MongoListener(Main.mongoHandler);
    public ContainerService() {
        ArrayList<Document> arrayList = mongoListener.getLatestContainers();
        for (Document doc : arrayList){
            try {
                containers.add(new Container(Integer.parseInt(doc.get("id").toString()),
                        doc.get("name").toString(),
                        doc.get("location").toString(),
                        Float.parseFloat(doc.get("fillPercentage").toString()),
                        doc.get("lastUpdated").toString()));
            }
            catch (Exception e){}
        }
    }

    public Optional<Container> getContainer(int id) {
        Optional<Container> service = Optional.empty();
        for (Container container : containers) {
            if (container.getId() == id) {
                service = Optional.of(container);
                return  service;
            }
        }
        return service;
    }

    public List<Container> getAllContainers(){
        return containers;
    }
    public void updateContainers(){
        containers.clear();
        ArrayList<Document> arrayList = mongoListener.getLatestContainers();
        for (Document doc : arrayList){
            try {
                containers.add(new Container(Integer.parseInt(doc.get("id").toString()),
                        doc.get("name").toString(),
                        doc.get("location").toString(),
                        Float.parseFloat(doc.get("fillPercentage").toString()),
                        doc.get("lastUpdated").toString()));
            }
            catch (Exception e){}
        }
    }
}
