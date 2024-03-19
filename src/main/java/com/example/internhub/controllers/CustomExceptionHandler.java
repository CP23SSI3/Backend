package com.example.internhub.controllers;

import com.example.internhub.exception.ExpiredTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<Object> handleExpiredTokenException(ExpiredTokenException ex) {
        // Customize the response for the expired token exception
        String errorMessage = ex.getMessage();
        // You can also log the exception here if needed
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
