package com.example.weatherApp.advice;

import com.example.weatherApp.exception.InvalidInputException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

//    @ExceptionHandler(Exception.class)  // Catch-all for unexpected errors
//    public ResponseEntity<String> handleGenericException(Exception ex) {
//        return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
//    }

}
