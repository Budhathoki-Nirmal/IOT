package com.example.iotagro;

public class SwitchState {
    private String Humidity,Moisture,Temperature,Time;
    private int LEDStat,RelayFanStat,RelayStat;

    public SwitchState() {
    }

    public SwitchState(String humidity, String moisture, String temperature, String time, int LEDStat, int relayFanStat, int relayStat) {
        this.Humidity = humidity;
        this.Moisture = moisture;
        this.Temperature = temperature;
        this.Time = time;
        this.LEDStat = LEDStat;
        this.RelayFanStat = relayFanStat;
        this.RelayStat = relayStat;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getMoisture() {
        return Moisture;
    }

    public void setMoisture(String moisture) {
        Moisture = moisture;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getLEDStat() {
        return LEDStat;
    }

    public void setLEDStat(int LEDStat) {
        this.LEDStat = LEDStat;
    }

    public int getRelayFanStat() {
        return RelayFanStat;
    }

    public void setRelayFanStat(int relayFanStat) {
        RelayFanStat = relayFanStat;
    }

    public int getRelayStat() {
        return RelayStat;
    }

    public void setRelayStat(int relayStat) {
        RelayStat = relayStat;
    }
}
