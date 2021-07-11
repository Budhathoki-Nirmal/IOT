package com.example.iotagro;

public class Model {
    public Model(){}
    public String date;
    public String time;
    public String temp;
    public String hum;
    public String soil;

    public Model(String date,String time,String temp,String hum,String soil){
        this.date=date;
        this.time=time;
        this.temp=temp;
        this.hum=hum;
        this.soil=soil;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getSoil() {
        return soil;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }
}
