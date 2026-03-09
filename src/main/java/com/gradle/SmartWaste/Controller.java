package com.gradle.SmartWaste;

import com.example.MongoListener;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// setting up API, needing many improvements
@RestController
@RequestMapping("/container-management")
public class Controller {
    MongoListener mongoListener = new MongoListener(Main.mongoHandler);

    @CrossOrigin
    @GetMapping("/containers")
    public List<Document> getContainerMeasurements() {
        // GET returns latest container measurements
        return mongoListener.getLatestContainers();
    }

    @PostMapping("/containers")
    public void addContainerMeasurement(@RequestBody Document document) {
        // Alert flag reset?
        // Mqtt publish to send reset signal to data simulator
        System.out.println("Received Message: " + document);
        mongoListener.addContainerMeasurement(document);
    }
}
