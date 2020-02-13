package com.challenge.weather.service;

import com.challenge.weather.model.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RubieraService {

    final
    OWMService owmService;

    final
    OCDService ocdService;

    public RubieraService(OWMService owmService, OCDService ocdService) {
        this.owmService = owmService;
        this.ocdService = ocdService;
    }

    public WeatherInfo getWeatherInfoForLocation(double lat, double lon) {
        return new WeatherInfo();
    }

    public WeatherInfo getWeatherInfoFromCityId(String cityId) {
        return owmService.getWeatherUpdateForCity(cityId);
    }


}
