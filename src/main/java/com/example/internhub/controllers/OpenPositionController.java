package com.example.internhub.controllers;

import com.example.internhub.responses.ResponseObjectList;
import com.example.internhub.services.OpenPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/open-positions")
@CrossOrigin(origins = "${cors.allow.origin}")
public class OpenPositionController {

    @Autowired
    private OpenPositionService openPositionService;

    @GetMapping("")
    public ResponseObjectList getAllOpenPositions(){
        return openPositionService.getAllOpenPositions();
    }

}
