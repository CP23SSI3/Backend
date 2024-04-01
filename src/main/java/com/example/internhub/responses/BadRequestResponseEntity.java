package com.example.internhub.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BadRequestResponseEntity extends ResponseEntity {
    public BadRequestResponseEntity(Exception e) {
        super(new ResponseObject(400 ,e.getMessage(), null),
                HttpStatus.BAD_REQUEST);
    }
}
