package com.example.zadanie3.model;

public class Train {

    private String number;
    private String carrier;
    private String direction;
    private int platform;
    private String time;
    private String status;

    public Train(String number, String carrier, String direction, int platform, String time, String status) {
        this.number = number;
        this.carrier = carrier;
        this.direction = direction;
        this.platform = platform;   
        this.time = time;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public String getCarrier() {
        return carrier;
    }

    public String getDirection() {
        return direction;
    }

    public int getPlatform() {
        return platform;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}