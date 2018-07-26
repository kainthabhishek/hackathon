package com.expedia.hackathon.project101.service;

import org.springframework.http.HttpStatus;

public class ImageDetailsException extends Exception{
    private HttpStatus statusCode;

    public ImageDetailsException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
