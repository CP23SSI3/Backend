package com.example.internhub.Controller;

import com.example.internhub.Company;
import com.example.internhub.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TestController {

    @Value("${minio.access.name}")
    private String access;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/test")
    public List<Company> test(){
        return companyRepository.findAll();
//        return "test";
    }

    @GetMapping("/test2")
    public String test2(){
        return access;
    }
}
