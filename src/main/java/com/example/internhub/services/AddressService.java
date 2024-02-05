package com.example.internhub.services;

import com.example.internhub.dtos.CreateAddressDTO;
import com.example.internhub.entities.Address;
import com.example.internhub.exception.AddressNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    public ResponseEntity getAllAddresses();
    public ResponseEntity getAddressById(String addressId);
    public Address createAddress(CreateAddressDTO createAddressPostDTO);
    public Address getAddressByAddressId(String addressId) throws AddressNotFoundException;
    public Address getAddress(Address address);
    public void updateAddress(Address oldAddress, Address newAddress);
}
