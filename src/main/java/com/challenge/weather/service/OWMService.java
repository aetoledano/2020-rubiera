package com.challenge.weather.service;

import com.challenge.weather.exceptions.CityNotFoundException;
import com.challenge.weather.exceptions.DataNotAvailable;
import com.challenge.weather.model.WeatherInfo;
import com.challenge.weather.model.openweathermap.City;
import com.challenge.weather.model.openweathermap.Main;
import com.challenge.weather.model.openweathermap.Weather;
import com.challenge.weather.model.openweathermap.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static com.challenge.weather.service.Constants.*;

//stands for OpenWeatherMapService
//this service is restricted to updates interval of 10 minutes

@Service
@Log4j2
public class OWMService implements InitializingBean {

    private final ObjectMapper jsonMapper;

    private final RestTemplate restTemplate;

    private ConcurrentHashMap<String, Long> times;
    private ConcurrentHashMap<String, WeatherInfo> reports;
    public final HashMap<String, City> cities;
    public final HashSet<String> citiesIds;


    ThreadPoolExecutor executor;

    public OWMService(ObjectMapper jsonMapper, RestTemplate restTemplate) {
        this.jsonMapper = jsonMapper;
        this.times = new ConcurrentHashMap<>();
        this.reports = new ConcurrentHashMap<>();
        this.cities = new HashMap<>();
        this.citiesIds = new HashSet<>();
        this.restTemplate = restTemplate;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Reading cities data");
        final InputStream input = Thread.
                currentThread().
                getContextClassLoader().
                getResourceAsStream("cities.json");

        for (City c : jsonMapper.readValue(input, City[].class)) {
            cities.put(c.getName().trim().toLowerCase(), c);
            citiesIds.add(c.getId());
        }

        log.info("Readed " + cities.size() + " cities");
    }

    public WeatherInfo getWeatherUpdateForCityName(String name)
            throws CityNotFoundException, DataNotAvailable {
        final City city = cities.get(name.toLowerCase());
        if (city == null) {
            throw new CityNotFoundException(name);
        }
        return getWeatherUpdateForCity(city.getId());
    }

    public WeatherInfo getWeatherUpdateForCity(String cityId)
            throws CityNotFoundException, DataNotAvailable {
        if (!citiesIds.contains(cityId)) {
            throw new CityNotFoundException(cityId);
        }
        updateAccessTime(cityId, true);
        WeatherInfo wi = reports.get(cityId);
        if (wi == null)
            throw new DataNotAvailable();
        return wi;
    }

    public WeatherInfo getWeatherUpdateForZip(String zip) {
        return null;
    }

    private synchronized void updateAccessTime(String key, boolean byCityId) {
        Long lastTime = times.get(key);
        if (lastTime == null) {
            times.put(key, lastTime = System.currentTimeMillis());
            executor.execute(() -> {
                if (byCityId) {
                    updateWeatherInfoByCityKey(key);
                } else {
                    updateWeatherInfoByZipCode(key);
                }
            });
        }
        if (System.currentTimeMillis() - lastTime > OPEN_WEATHER_MAP_REFRESH_INTERVAL) {
            times.put(key, System.currentTimeMillis());
            executor.execute(() -> {
                if (byCityId) {
                    updateWeatherInfoByCityKey(key);
                } else {
                    updateWeatherInfoByZipCode(key);
                }
            });
        }
    }

    private void updateWeatherInfoByZipCode(String key) {
    }

    private void updateWeatherInfoByCityKey(String cityId) {
        String uri = WEATHER_CITY_ID_URI.
                replace(CITY_ID_PLACEHOLDER, cityId).
                replace(KEY_PLACEHOLDER, OPEN_WEATHER_MAP_KEY);

        try {
            WeatherResponse response = restTemplate.getForObject(uri, WeatherResponse.class);
            final WeatherInfo weatherInfo = proccessWeatherResponse(response);
            reports.put(cityId, weatherInfo);
        } catch (Exception any) {
            log.error(any);
        }
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


}
