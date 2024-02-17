package com.example.internhub.controllers;

import com.example.internhub.dtos.EditAddressDTO;
import com.example.internhub.responses.ResponseObjectList;
import com.example.internhub.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @PutMapping("/{addressId}")
    public ResponseEntity editAddress(@PathVariable String addressId,
                                      @RequestBody EditAddressDTO editAddressDTO) {
        return addressService.editAddress(addressId, editAddressDTO);
    }
}
