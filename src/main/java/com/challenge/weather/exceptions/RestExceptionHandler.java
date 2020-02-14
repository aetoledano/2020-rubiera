package com.challenge.weather.exceptions;

import com.challenge.weather.model.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    ResponseEntity handleResourceNotFoundException(CityNotFoundException ex) {

        ApiError err = new ApiError();
        err.setCode(HttpStatus.NOT_FOUND.value());
        err.setMsg(ex.getMessage());

        return new ResponseEntity(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataNotAvailable.class)
    ResponseEntity handleResourceNotFoundException(DataNotAvailable ex) {

        ApiError err = new ApiError();
        err.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        err.setHttpReason(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
        err.setMsg(ex.getMessage());

        return new ResponseEntity(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(GeolocationServiceUnavailable.class)
    ResponseEntity handleResourceNotFoundException(GeolocationServiceUnavailable ex) {

        ApiError err = new ApiError();
        err.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        err.setHttpReason(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        err.setMsg(ex.getMessage());

        return new ResponseEntity(err, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
