package com.byteworks.byteworksfoodapp.Exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message, HttpStatus badRequest) {
        super(message);
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}