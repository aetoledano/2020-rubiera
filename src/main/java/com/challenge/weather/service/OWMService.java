package com.challenge.weather.service;

import com.challenge.weather.model.WeatherInfo;
import com.challenge.weather.model.openweathermap.Main;
import com.challenge.weather.model.openweathermap.Weather;
import com.challenge.weather.model.openweathermap.WeatherResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;

import static com.challenge.weather.service.Constants.*;

//stands for OpenWeatherMapService
//this service is restricted to updates interval of 10 minutes

@Service
public class OWMService implements InitializingBean {

    private final RestTemplate restTemplate;

    private ConcurrentHashMap<String, WeatherStatus> weatherReports;

    public OWMService(@Autowired RestTemplate restTemplate) {
        this.weatherReports = new ConcurrentHashMap<>();
        this.restTemplate = restTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //read cities data here
    }

    public WeatherInfo getWeatherUpdateForZip(String zip) {
//        String URL = WEATHER_ZIP_CODE_URI;
//        restTemplate.getForObject()
        return null;
    }

    public WeatherInfo getWeatherUpdateForCity(String cityId) {
        String uri = WEATHER_CITY_ID_URI.
                replace(CITY_ID_PLACEHOLDER, cityId).
                replace(KEY_PLACEHOLDER, OPEN_WEATHER_MAP_KEY);

        WeatherResponse response = restTemplate.getForObject(uri, WeatherResponse.class);

        return proccessWeatherResponse(response);
    }

    private WeatherInfo proccessWeatherResponse(WeatherResponse response) {
        WeatherInfo info = new WeatherInfo();

        info.setName(response.getName());

        final Weather w = response.getWeather().get(0);
        info.setSky(w.getMain());
        info.setSkyDesc(w.getDescription());
        info.setSkyImageUri(WEATHER_ICON_URI.replace(ICON_ID_PLACEHOLDER, w.getIcon()));

        final Main m = response.getMain();
        info.setTemp(m.getTemp());
        info.setActualFeel(m.getFeels_like());
        info.setHumidityPercentage(m.getHumidity());
        if (response.getClouds() != null)
            info.setCloudsPercentage(response.getClouds().getAll());

        return info;
    }

    static class WeatherStatus {
        long lastTimeRequested;
        WeatherInfo data;
    }

}
