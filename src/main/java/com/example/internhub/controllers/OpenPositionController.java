package com.example.internhub.controllers;

import com.example.internhub.entities.OpenPosition;
import com.example.internhub.services.OpenPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/open-positions")
public class OpenPositionController {

    @Autowired
    private OpenPositionService openPositionService;

    @GetMapping("")
    public List<OpenPosition> getAllOpenPositions(){
        return openPositionService.getAllOpenPositions();
    }
}
