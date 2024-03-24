package com.example.internhub.controllers;

import com.example.internhub.dtos.CreateEducationDTO;
import com.example.internhub.services.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/educations")
@CrossOrigin(origins = "${cors.allow.origin}")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @PostMapping("")
    private ResponseEntity addEducation(@RequestBody @Valid CreateEducationDTO createEducationDTO,
                                        HttpServletRequest req) {
        return educationService.addEducation(createEducationDTO, req);
    }

    @GetMapping("")
    public ResponseEntity getAllEducations(){
        return educationService.getAllEducations();
    }
}
