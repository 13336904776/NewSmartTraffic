package com.example.mrzhang.newsmarttraffic.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 */
@DatabaseTable(tableName = "sense")
public class Sense {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "pm25")
    private int pm25;
    @DatabaseField(columnName = "co2")
    private int co2;
    @DatabaseField(columnName = "temperature")
    private int temperature;
    @DatabaseField(columnName = "lightIntensity")
    private int lightIntensity;
    @DatabaseField(columnName = "humidity")
    private int humidity;

    public Sense() {
    }

    public Sense(int id, int pm25, int co2, int temperature, int lightIntensity, int humidity) {
        this.id = id;
        this.pm25 = pm25;
        this.co2 = co2;
        this.temperature = temperature;
        this.lightIntensity = lightIntensity;
        this.humidity = humidity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
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
        return lightIntensity;
    }

    public void setLightIntensity(int lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
