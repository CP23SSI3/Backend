package com.example.internhub.config;

import com.example.internhub.responses.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity processRuntimeException(RuntimeException ex) {
        return new ResponseEntity(new ResponseObject(400, ex.getMessage(), null),
                null, HttpStatus.BAD_REQUEST);
    }

}
