package com.example.internhub.controllers;

import com.example.internhub.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/langs")
@CrossOrigin(origins = "${cors.allow.origin}")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping("")
    public ResponseEntity getAllLanguages(){
        return languageService.getAllLanguages();
    }

}
