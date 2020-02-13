package com.challenge.weather.model.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse implements Serializable {

    @JsonProperty("weather")
    List<Weather> weather;

    @JsonProperty("main")
    Main main;

    @JsonProperty("clouds")
    Clouds clouds;

    @JsonProperty("name")
    String name;

    @JsonProperty("cod")
    int cod;

    @JsonProperty("dt")
    long dt;

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "weather=" + weather +
                ", main=" + main +
                ", clouds=" + clouds +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                ", dt=" + dt +
                '}';
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }
}
