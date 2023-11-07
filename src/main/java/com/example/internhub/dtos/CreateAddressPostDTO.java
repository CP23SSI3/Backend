package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class CreateAddressPostDTO {
    String addressId = UUID.randomUUID().toString();
    String country;
    String postalCode;
    String city;
    String district;
    String subDistrict;
    String area;
    String latitude;
    String longitude;
}
