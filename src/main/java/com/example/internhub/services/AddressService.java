package com.example.internhub.services;

import com.example.internhub.dtos.CreateAddressDTO;
import com.example.internhub.dtos.EditAddressDTO;
import com.example.internhub.entities.Address;
import com.example.internhub.exception.AddressNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {
    public Address createAddress(CreateAddressDTO createAddressPostDTO);
    public void deleteAddress(Address address);
    public ResponseEntity editAddress(String addressId, EditAddressDTO editAddressDTO);
    public Address getAddress(Address address);
    public Address getAddressByAddressId(String addressId) throws AddressNotFoundException;
    public ResponseEntity getAddressById(String addressId);
    public ResponseEntity getAllAddresses();
    public void updateAddress(Address oldAddress, Address newAddress);
}
