package com.example.internhub.controllers;

import com.example.internhub.services.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/educations")
@CrossOrigin(origins = "${cors.allow.origin}")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @GetMapping("")
    public ResponseEntity getAllEducations(){
        return educationService.getAllEducations();
    }
}
