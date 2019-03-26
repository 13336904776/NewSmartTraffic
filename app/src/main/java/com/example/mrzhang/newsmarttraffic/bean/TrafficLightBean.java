package com.example.mrzhang.newsmarttraffic.bean;

/**
 *
 */
public class TrafficLightBean {
    private int roadId;
    private int red;
    private int yellow;
    private int green;

    public TrafficLightBean() {
    }

    public TrafficLightBean(int roadId, int red, int yellow, int green) {
        this.roadId = roadId;
        this.red = red;
        this.yellow = yellow;
        this.green = green;
    }

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }
}
