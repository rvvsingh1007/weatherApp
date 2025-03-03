package com.example.weatherApp.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class InvalidInputException extends RuntimeException {
    private final HttpStatus status;

    public InvalidInputException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
