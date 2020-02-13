package com.challenge.weather.model;

import lombok.Data;

@Data
public class WeatherInfo {

    String name;
    String skyImageUri;
    String sky;
    String skyDesc;
    double temp;
    double actualFeel;
    int humidityPercentage;
    int cloudsPercentage;

}
