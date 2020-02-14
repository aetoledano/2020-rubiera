package com.challenge.weather.controller;

import com.challenge.weather.exceptions.CityNotFoundException;
import com.challenge.weather.exceptions.DataNotAvailable;
import com.challenge.weather.exceptions.GeolocationServiceUnavailable;
import com.challenge.weather.service.RubieraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherApi {

    final
    RubieraService rubiera;

    public WeatherApi(RubieraService rubiera) {
        this.rubiera = rubiera;
    }

    @GetMapping
    public ResponseEntity getWeatherByLocation(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon) throws DataNotAvailable, CityNotFoundException, GeolocationServiceUnavailable {
        return ResponseEntity.ok(rubiera.getWeatherInfoForLocation(lat, lon));
    }

    @GetMapping(value = "/{cityId}")
    public ResponseEntity getWeatherByCityId(
            @PathVariable("cityId") String cityId) throws DataNotAvailable, CityNotFoundException {
        return ResponseEntity.ok(rubiera.getWeatherInfoFromCityId(cityId));
    }


}
