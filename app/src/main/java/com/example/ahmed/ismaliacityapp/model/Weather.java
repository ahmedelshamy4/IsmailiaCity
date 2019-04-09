package com.example.ahmed.ismaliacityapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Weather implements Serializable {
    @SerializedName("icon")
    private String icon;
    @SerializedName("time")
    private String time;
    @SerializedName("text")
    private String text;
    @SerializedName("temp")
    private Double temp;


    public Weather() {
    }

    public Weather(String icon, String time, String text, Double temp) {
        this.icon = icon;
        this.time = time;
        this.text = text;
        this.temp = temp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
}
