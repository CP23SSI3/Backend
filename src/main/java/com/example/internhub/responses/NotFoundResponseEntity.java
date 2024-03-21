package com.example.internhub.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NotFoundResponseEntity extends ResponseEntity {
    public NotFoundResponseEntity(Exception e){
        super(new ResponseObject(404 ,e.getMessage(), null),
                 HttpStatus.NOT_FOUND);
       ;
    }
}
