package com.example.internhub.services;

import com.example.internhub.repositories.EducationRepositories;
import com.example.internhub.responses.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Primary
public class MySQLEducationService implements EducationService{

    @Autowired
    private EducationRepositories educationRepositories;

    @Override
    public ResponseEntity getAllEducations() {
        return new ResponseEntity(new ResponseObject(200, "Education's list is succesfully sended.", educationRepositories.findAll()),
                null, HttpStatus.OK);
    }
}
