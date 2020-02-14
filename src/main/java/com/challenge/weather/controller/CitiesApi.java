package com.challenge.weather.controller;

import com.challenge.weather.service.OWMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
public class CitiesApi {

    @Autowired
    OWMService srv;

    @GetMapping
    public ResponseEntity getWeatherByLocation() {
        return ResponseEntity.ok(srv.cities);
    }
}
