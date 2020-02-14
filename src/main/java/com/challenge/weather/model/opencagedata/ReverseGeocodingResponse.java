package com.challenge.weather.model.opencagedata;

import lombok.Data;

import java.util.List;

@Data
public class ReverseGeocodingResponse {

    Rate rate;
    List<LocationData> results;

}
