package com.challenge.weather.model.opencagedata;

import lombok.Data;

@Data
public class Rate {
    int limit, remaining;
    long reset;
}
