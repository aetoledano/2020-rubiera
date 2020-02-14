package com.challenge.weather.service;

import com.challenge.weather.exceptions.CityNotFoundException;
import com.challenge.weather.exceptions.DataNotAvailable;
import com.challenge.weather.exceptions.GeolocationServiceUnavailable;
import com.challenge.weather.model.WeatherInfo;
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

    public WeatherInfo getWeatherInfoForLocation(double lat, double lon)
            throws DataNotAvailable, CityNotFoundException, GeolocationServiceUnavailable {
        final String cityName = ocdService.getCityName(lat, lon);
        return owmService.getWeatherUpdateForCityName(cityName);
    }

    public WeatherInfo getWeatherInfoFromCityId(String cityId)
            throws DataNotAvailable, CityNotFoundException {
        return owmService.getWeatherUpdateForCity(cityId);
    }


}
