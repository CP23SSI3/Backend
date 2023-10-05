package com.example.internhub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TestController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/test")
    public List<Company> test(){
        return companyRepository.findAll();
//        return "test";
    }
}
