package com.example.iotagro;

public class Plant {

    String max_hum,max_moist,max_temp,min_hum,min_moist,min_temp,plant;

    Plant(){

    }

    public Plant(String max_hum, String max_moist, String max_temp, String min_hum, String min_moist, String min_temp, String plant) {
        this.max_hum = max_hum;
        this.max_moist = max_moist;
        this.max_temp = max_temp;
        this.min_hum = min_hum;
        this.min_moist = min_moist;
        this.min_temp = min_temp;
        this.plant = plant;
    }

    public String getMax_hum() {
        return max_hum;
    }

    public void setMax_hum(String max_hum) {
        this.max_hum = max_hum;
    }

    public String getMax_moist() {
        return max_moist;
    }

    public void setMax_moist(String max_moist) {
        this.max_moist = max_moist;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getMin_hum() {
        return min_hum;
    }

    public void setMin_hum(String min_hum) {
        this.min_hum = min_hum;
    }

    public String getMin_moist() {
        return min_moist;
    }

    public void setMin_moist(String min_moist) {
        this.min_moist = min_moist;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }
}
