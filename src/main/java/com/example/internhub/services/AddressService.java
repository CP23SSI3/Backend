package com.example.internhub.services;

import com.example.internhub.dtos.CreateAddressDTO;
import com.example.internhub.entities.Address;
import com.example.internhub.repositories.AddressRepository;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    public ResponseObjectList getAllAddresses();
    public ResponseObject getAddressById(String addressId);
    public Address createAddress(CreateAddressDTO createAddressPostDTO);
    public Address getAddressByAddressId(String addressId);
    public Address getAddress(Address address);
}
