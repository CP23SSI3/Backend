package com.example.internhub.services;

import com.example.internhub.dtos.CreateAddressDTO;
import com.example.internhub.entities.Address;
import com.example.internhub.exception.AddressNotFoundException;
import com.example.internhub.repositories.AddressRepository;
import com.example.internhub.responses.ResponseObject;
import org.hibernate.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import com.example.internhub.responses.ResponseObjectList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Primary
public class MySQLAddressService implements AddressService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public Address createAddress(CreateAddressDTO createAddressPostDTO) {
        Address address = modelMapper.map(createAddressPostDTO, Address.class);
        addressRepository.save(address);
        return address;
    }

    @Override
    public Address getAddress(Address address) {
        try {
            return new Address(address.getAddressId(), address.getArea(),
                    address.getCity(), address.getCountry(),
                    address.getDistrict(), address.getLatitude(), address.getLongitude(),
                    address.getPostalCode(),
                    address.getSubDistrict());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Address getAddressByAddressId(String addressId) throws AddressNotFoundException {
        try {
            return addressRepository.findById(addressId).orElseThrow();
        } catch (Exception ex) {
            throw new AddressNotFoundException(addressId);
        }
    }

    @Override
    public ResponseEntity getAddressById(String addressId) {
        try {
            return new ResponseEntity(new ResponseObject(200, ("Address id " + addressId + " is successfully sent."), getAddressByAddressId(addressId)),
                    null,
                    HttpStatus.OK);
        } catch (AddressNotFoundException ex) {
            return new ResponseEntity(new ResponseObject(404, ex.getMessage(), null),
                    null,
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity getAllAddresses() {
        return new ResponseEntity(new ResponseObjectList(200, "Address's list is successfully sent.", addressRepository.findAll()),
                null,
                HttpStatus.OK);
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
