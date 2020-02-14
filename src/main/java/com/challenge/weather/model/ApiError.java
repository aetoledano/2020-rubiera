package com.challenge.weather.model;

import lombok.Data;

@Data
public class ApiError {
    int code;
    String httpReason;
    String msg;
}
