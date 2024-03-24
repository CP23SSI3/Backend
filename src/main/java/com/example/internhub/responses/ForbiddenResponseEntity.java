package com.example.internhub.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ForbiddenResponseEntity extends ResponseEntity {
    public ForbiddenResponseEntity (Exception e) {
        super(new ResponseObject(403 ,e.getMessage(), null),
                HttpStatus.FORBIDDEN);}
}
