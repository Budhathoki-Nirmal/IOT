package com.example.iotagro;

public class Model {
    public Model(){}
    public String date;
    public String temp;
    public String hum;
    public String soil;

    public Model(String date,String temp,String hum,String soil){
        this.date=date;
        this.temp=temp;
        this.hum=hum;
        this.soil=soil;
    }

    public void setDate(String date){
        this.date=date;
    }
    public void setTemp(String temp){
        this.temp=temp;
    }
    public void setSoil(String soil){
        this.soil=soil;
    }
    public void setHum(String hum){
        this.hum=hum;
    }

    public String getDate() {
        return date;
    }

    public String getTemp() {
        return temp;
    }

    public String getHum() {
        return hum;
    }

    public String getSoil() {
        return soil;
    }
}
