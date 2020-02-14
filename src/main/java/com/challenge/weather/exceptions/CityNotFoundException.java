package com.challenge.weather.exceptions;

public class CityNotFoundException extends Exception {
    public CityNotFoundException(String cityId) {
        super("City " + cityId + " not found");
    }
}
