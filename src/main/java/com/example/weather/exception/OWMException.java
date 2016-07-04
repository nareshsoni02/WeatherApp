package com.example.weather.exception;

import org.springframework.http.HttpStatus;

import java.io.IOException;

/**
 * Created by moksha on 03/07/2016.
 */
public class OWMException extends IOException {
    private HttpStatus statusCode;

    private String body;

    public OWMException(String msg) {
        super(msg);
    }

    public OWMException(HttpStatus statusCode, String msg) {
        super(msg);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
