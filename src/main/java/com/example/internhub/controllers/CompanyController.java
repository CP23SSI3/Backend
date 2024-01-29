package com.example.internhub.controllers;

import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import com.example.internhub.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


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

    @GetMapping("/{compId}")
    public ResponseObject getCompanyById(@PathVariable String compId,
                                         HttpServletResponse res) {
        return companyService.getCompanyById(compId, res);
    }
}
