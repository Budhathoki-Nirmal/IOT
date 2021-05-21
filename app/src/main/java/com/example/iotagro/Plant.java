package com.example.iotagro;

public class Plant {
    String name;
    String tempMin;
    String tempMax;
    String humMin;
    String humMax;
    String moistMin;
    String moistMax;

    public Plant(String name, String tempMin, String tempMax, String humMin, String humMax, String moistMin, String moistMax) {
        this.name = name;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humMin = humMin;
        this.humMax = humMax;
        this.moistMin = moistMin;
        this.moistMax = moistMax;
    }

    public String getName() {
        return name;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getHumMin() {
        return humMin;
    }

    public String getHumMax() {
        return humMax;
    }

    public String getMoistMin() {
        return moistMin;
    }

    public String getMoistMax() {
        return moistMax;
    }
}
