package com.example.internhub.controllers;

import com.example.internhub.responses.ResponseObjectList;
import com.example.internhub.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/companies")
@CrossOrigin(origins = "${cors.allow.origin}")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("")
    public ResponseObjectList getAllCompanies(){
        return companyService.getAllCompanies();
    }
}
