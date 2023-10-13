package com.example.internhub.controllers;

import com.example.internhub.entities.Company;
import com.example.internhub.entities.Address;
import com.example.internhub.entities.Post;
import com.example.internhub.repositories.AddressRepository;
import com.example.internhub.repositories.CompanyRepository;
import com.example.internhub.repositories.PostRepository;
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

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/test")
    public List<Address> test(){
        return addressRepository.findAll();
    }

    @GetMapping("/test2")
    public List<Company> test2(){
        return companyRepository.findAll();
    }

    @GetMapping("/test3")
    public List<Post> test3() {
        return postRepository.findAll();
    }
}
