package com.gradle.SmartWaste;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/containers")
public class Controller {
    private SubscriberService subscriberService;
    private ContainerService containerService;

    @Autowired
    public Controller(SubscriberService subscriberService, ContainerService containerService) {
        this.subscriberService = subscriberService;
        this.containerService = containerService;
    }

    @CrossOrigin
    @GetMapping("/container")
    public List<Container> getAllContainers(){
        containerService.updateContainers();
        return  containerService.getAllContainers();
    }
    @GetMapping("/container/{id}")
    public Optional<Container> getContainer(@PathVariable String id){
        containerService.updateContainers();
        return containerService.getContainer(Integer.parseInt(id));
    }
    @GetMapping("/subscriber")
    public List<Subscriber> getSubs(){
        return subscriberService.getAllSubscribers();
    }
    @GetMapping("/subscriber/{id}")
    public Optional<Subscriber> getSubscriber(@PathVariable String id){
        return subscriberService.getSubscriber(Integer.parseInt(id));
    }
    @PostMapping("/subscriber")
    public void saveSubscriber(@RequestBody Subscriber subscriber){
        subscriberService.add(subscriber);
    }
}
