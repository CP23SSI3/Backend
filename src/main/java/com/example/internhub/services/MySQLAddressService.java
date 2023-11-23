package com.example.internhub.services;

import com.example.internhub.dtos.CreateAddressDTO;
import com.example.internhub.entities.Address;
import com.example.internhub.repositories.AddressRepository;
import com.example.internhub.responses.ResponseObject;
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
    public ResponseObject getAddressById(String addressId) {
        return new ResponseObject(200,
                ("Address id " + addressId + " is successfully sended."),
                getAddressByAddressId(addressId));
    }

    @Override
    public Address createAddress(CreateAddressDTO createAddressPostDTO) {
        Address address = modelMapper.map(createAddressPostDTO, Address.class);
        System.out.println(address);
        addressRepository.save(address);
        return address;
    }

    @Override
    public Address getAddressByAddressId(String addressId) {
        return addressRepository.getById(addressId);
    }

    @Override
    public Address getAddress(Address address) {
        return new Address(address.getAddressId(), address.getArea(),
                address.getCity(), address.getCountry(),
                address.getDistrict(), address.getLatitude(), address.getLongitude(),
                address.getPostalCode(),
                address.getSubDistrict());
    }

    @Override
    public void updateAddress(Address oldAddress, Address newAddress) {
        oldAddress.setArea(newAddress.getArea());
        oldAddress.setCity(newAddress.getCity());
        oldAddress.setCountry(newAddress.getCountry());
        oldAddress.setDistrict(newAddress.getDistrict());
        oldAddress.setLatitude(newAddress.getLatitude());
        oldAddress.setLongitude(newAddress.getLongitude());
        oldAddress.setPostalCode(newAddress.getPostalCode());
        oldAddress.setSubDistrict(newAddress.getSubDistrict());
    }

}
