package com.gradle.SmartWaste;

import com.example.MongoListener;
import org.springframework.stereotype.Service;
import org.bson.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {
    private List<Subscriber> subscribers = new ArrayList<>();
    MongoListener mongoListener = new MongoListener(Main.mongoHandler);
    public SubscriberService() {
        ArrayList<Document> arrayList = mongoListener.getSubscribers();
        for (Document doc : arrayList){
            try {
                subscribers.add(new Subscriber(Integer.parseInt(doc.get("_id").toString()),
                        doc.get("name").toString(),
                        doc.get("email").toString(),
                        doc.get("phone").toString(),
                        doc.get("role").toString()));
            }
            catch (Exception e){}
        }
    }

    public Optional<Subscriber> getSubscriber(int id) {
        Optional<Subscriber> service = Optional.empty();
        for (Subscriber subscriber : subscribers) {
            if (subscriber.getId() == id) {
                service = Optional.of(subscriber);
                return  service;
            }
        }
        return service;
    }

    public List<Subscriber> getAllSubscribers(){
        return subscribers;
    }
    public void add(Subscriber subscriber){
        subscribers.add(subscriber);
        Document subscriberDoc = new Document();
        subscriberDoc.put("_id", subscriber.getId());
        subscriberDoc.put("name", subscriber.getName());
        subscriberDoc.put("email", subscriber.getEmail());
        subscriberDoc.put("phone", subscriber.getPhone());
        subscriberDoc.put("role", subscriber.getRole());
        subscriberDoc.put("created_at", Date.from(Instant.now()));
        mongoListener.addSubscriber(subscriberDoc);
    }
}
