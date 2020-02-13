package com.challenge.weather.controller;

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
            @RequestParam("page") double lat,
            @RequestParam("size") double lon) {
        return ResponseEntity.ok(rubiera.getWeatherInfoForLocation(lat, lon));
    }

    @GetMapping(value = "/{cityId}")
    public ResponseEntity getWeatherByCityId(
            @PathVariable("cityId") String cityId) {
        return ResponseEntity.ok(rubiera.getWeatherInfoFromCityId(cityId));
    }


}
