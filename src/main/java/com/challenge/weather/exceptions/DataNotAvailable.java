package com.challenge.weather.exceptions;

public class DataNotAvailable extends Exception {
    public DataNotAvailable() {
        super("There is not data still available for this location.");
    }
}
