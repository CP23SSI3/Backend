package com.example.internhub.controllers;

import com.example.internhub.responses.ResponseObjectList;
import com.example.internhub.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/addresses")
@CrossOrigin(origins = "${cors.allow.origin}")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity getAllAddresses(){
        return addressService.getAllAddresses();
    }
}
