package com.example.internhub.controllers;

import com.example.internhub.entities.Company;
import com.example.internhub.entities.Address;
import com.example.internhub.repositories.AddressRepository;
import com.example.internhub.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/test")
    public List<Address> test(){
        return addressRepository.findAll();
//        return companyRepository.findAll();
//        return "test";
    }

    @GetMapping("/test2")
    public List<Company> test2(){
        return companyRepository.findAll();
//        return companyRepository.findAll();
//        return "test";
    }
}
