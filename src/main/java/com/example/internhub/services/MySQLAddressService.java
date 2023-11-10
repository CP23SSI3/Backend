package com.example.internhub.services;

import com.example.internhub.dtos.CreateAddressDTO;
import com.example.internhub.entities.Address;
import com.example.internhub.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MySQLAddressService implements AddressService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(String addressId) {
        return addressRepository.getById(addressId);
    }

    @Override
    public Address createAddress(CreateAddressDTO createAddressPostDTO) {
        Address address = modelMapper.map(createAddressPostDTO, Address.class);
        System.out.println(address);
        addressRepository.save(address);
        return address;
    }

}
