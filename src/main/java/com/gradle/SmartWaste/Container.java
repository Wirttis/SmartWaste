package com.gradle.SmartWaste;

public class Container {

    private int id;
    private String name;
    private String location;
    private float fillPercentage;
    private String lastUpdated;

    public Container(int id, String name, String location, float fillPercentage, String lastUpdated) {
        this.id = id;
        this.fillPercentage = fillPercentage;
        this.name = name;
        this.location = location;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public float getFillPercentage() {
        return fillPercentage;
    }
    public void setFillPercentage(float fillPercentage) {
        this.fillPercentage = fillPercentage;
    }
    public String getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}

