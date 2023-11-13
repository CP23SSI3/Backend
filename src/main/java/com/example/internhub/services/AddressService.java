package com.example.internhub.services;

import com.example.internhub.entities.Address;
import com.example.internhub.repositories.AddressRepository;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressService {
    public ResponseObjectList getAllAddresses();
    public Address getAddressById(String addressId);
}
