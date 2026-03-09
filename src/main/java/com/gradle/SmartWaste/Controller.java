package com.gradle.SmartWaste;

import com.example.MongoListener;
import org.bson.Document;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// setting up API, needing many improvements
@RestController
@RequestMapping("/containers")
public class Controller {
    @CrossOrigin
    @GetMapping("/api/v1/test")
    public List<Document> test(){
        // GET returns latest container measurements
        MongoListener mongoListener = new MongoListener(Main.mongoHandler);
        return  mongoListener.getLatestContainers();
    }
}
