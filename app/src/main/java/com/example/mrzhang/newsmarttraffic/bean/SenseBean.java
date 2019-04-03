package com.example.mrzhang.newsmarttraffic.bean;

import com.google.gson.annotations.SerializedName;

public class SenseBean {

    @SerializedName("pm2.5")
    private int _$Pm25201; // FIXME check this code
    private String ERRMSG;
    private int co2;
    private int temperature;
    private int LightIntensity;
    private int humidity;
    private String RESULT;

    public int get_$Pm25201() {
        return _$Pm25201;
    }

    public void set_$Pm25201(int _$Pm25201) {
        this._$Pm25201 = _$Pm25201;
    }

    public String getERRMSG() {
        return ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getLightIntensity() {
        return LightIntensity;
    }

    public void setLightIntensity(int LightIntensity) {
        this.LightIntensity = LightIntensity;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }
}
