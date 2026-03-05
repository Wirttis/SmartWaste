package com.example;

public class Container {
    private final int fillPercentage;
    private final String containerId;
    private final String name;
    private final String location;
    private final String lastUpdated;

    public Container(int fillPercentage, String containerId, String name, String location, String lastUpdated) {
        this.fillPercentage = fillPercentage;
        this.containerId = containerId;
        this.name = name;
        this.location = location;
        this.lastUpdated = lastUpdated;
    }


}
