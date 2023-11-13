package com.example.internhub.services;

import com.example.internhub.dtos.CreateAddressDTO;
import com.example.internhub.entities.Address;
import com.example.internhub.repositories.AddressRepository;
import org.modelmapper.ModelMapper;
import com.example.internhub.responses.ResponseObjectList;
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
    public ResponseObjectList getAllAddresses() {

        return new ResponseObjectList(200,
                "Address's list is successfully sended.",
                addressRepository.findAll());
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

    @Override
    public Address getAddress(Address address) {
        return new Address(address.getAddressId(), address.getCountry(),
                address.getPostalCode(), address.getCity(),
                address.getDistrict(), address.getSubDistrict(),
                address.getArea(), address.getLatitude(), address.getLongitude());
    }

}
