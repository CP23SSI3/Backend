package com.example.internhub.services;

import com.example.internhub.entities.Address;
import com.example.internhub.repositories.AddressRepository;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLAddressService implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public ResponseObjectList getAllAddresses() {

        return new ResponseObjectList(200,
                "Address's list is successfully sended.",
                addressRepository.findAll());
    }

    @Override
    public Address getAddressById(String addressId) {
        return addressRepository.getById(addressId);
    }

}
