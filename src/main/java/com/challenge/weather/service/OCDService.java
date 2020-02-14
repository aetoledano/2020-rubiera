package com.challenge.weather.service;

import com.challenge.weather.exceptions.DataNotAvailable;
import com.challenge.weather.exceptions.GeolocationServiceUnavailable;
import com.challenge.weather.model.opencagedata.ReverseGeocodingResponse;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.challenge.weather.service.Constants.*;

//stands for OpenCageDataService
//this service is restricted to 2500 requests/day

@Log4j2
@Service
public class OCDService {

    private final RestTemplate restTemplate;

    private final ConcurrentHashMap<Location, String> locations;
    private final BlockingQueue<Location> locQueue;
    private boolean serviceLocked;
    private Date availableDate;

    public OCDService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.locations = new ConcurrentHashMap<>();
        locQueue = new LinkedBlockingQueue<>();
    }

    public String getCityName(double lat, double lon) throws DataNotAvailable, GeolocationServiceUnavailable {
        Location loc = new Location();
        loc.lat = lat;
        loc.lon = lon;
        final String cityName = locations.get(loc);

        if (cityName != null)
            return cityName;

        if (serviceLocked && System.currentTimeMillis() < availableDate.getTime()) {
            throw new GeolocationServiceUnavailable("Reached geolocation queries limit.");
        }

        try {
            locQueue.put(loc);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //should not happen
        }

        throw new DataNotAvailable();
    }

    @Scheduled(fixedRate = 1000)
    private void processGeolocationQueries() {
        final Location loc;
        try {
            loc = locQueue.poll(0, TimeUnit.SECONDS);
            if (loc == null)
                return;
        } catch (InterruptedException ex) {
            log.error("Error interrupting locations queue.");
            return;
        }

        if (locations.containsKey(loc)) return;

        String uri = REVERSE_GEOCODING_URI.
                replace(LAT_PLACEHOLDER, String.valueOf(loc.lat)).
                replace(LON_PLACEHOLDER, String.valueOf(loc.lon)).
                replace(KEY_PLACEHOLDER, OPEN_CAGE_DATA_KEY);

        try {
            ReverseGeocodingResponse response =
                    restTemplate.getForObject(uri, ReverseGeocodingResponse.class);
            serviceLocked = false;
            log.warn("OpenCageDataService"+response.getRate());
            availableDate = new Date(response.getRate().getReset());
            locations.put(loc, response.getResults().get(0).getComponents().getState());
        } catch (HttpClientErrorException ex) {
            log.error(ex);
            if (ex.getStatusCode().value() == 402) {
                serviceLocked = true;
            }
        }
    }

    @Data
    private class Location {
        double lat, lon;
    }
}
