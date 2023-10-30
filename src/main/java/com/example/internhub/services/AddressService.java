package com.example.internhub.services;

import com.example.internhub.entities.Address;
import com.example.internhub.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressService {
    public List<Address> getAllAddresses();
}
