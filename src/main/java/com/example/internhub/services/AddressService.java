package com.example.internhub.services;

import com.example.internhub.dtos.CreateAddressDTO;
import com.example.internhub.entities.Address;

import java.util.List;

public interface AddressService {
    public List<Address> getAllAddresses();
    public Address getAddressById(String addressId);
    public Address createAddress(CreateAddressDTO createAddressPostDTO);
    public Address getAddress(Address address);
}
