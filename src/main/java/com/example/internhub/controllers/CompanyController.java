package com.example.internhub.controllers;

import com.example.internhub.dtos.EditCompanyDTO;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import com.example.internhub.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/companies")
@CrossOrigin(origins = "${cors.allow.origin}")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PutMapping("/{compId}")
    public ResponseEntity editCompany(@RequestBody EditCompanyDTO editCompanyDTO,
                                      @PathVariable String compId,
                                      HttpServletRequest req) {
        return companyService.editCompany(editCompanyDTO, compId, req);
    }

    @GetMapping("")
    public ResponseEntity getAllCompanies(@RequestParam(defaultValue = "0") int pageNumber,
                                          @RequestParam(defaultValue = "10") int pageSize){
        return companyService.getAllCompanies(pageNumber, pageSize);
    }

    @GetMapping("/{compId}")
    public ResponseEntity getCompanyById(@PathVariable String compId,
                                         HttpServletResponse res) {
        return companyService.getCompanyById(compId, res);
    }
}
