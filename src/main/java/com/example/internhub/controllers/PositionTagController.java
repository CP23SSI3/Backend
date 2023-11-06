package com.example.internhub.controllers;

import com.example.internhub.entities.PositionTag;
import com.example.internhub.services.PositionTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/position-tags")
public class PositionTagController {

    @Autowired
    private PositionTagService positionTagService;

    @GetMapping
    public List<PositionTag> getAllPositionTags(){
        return positionTagService.getAllPositionTag();
    }

}
