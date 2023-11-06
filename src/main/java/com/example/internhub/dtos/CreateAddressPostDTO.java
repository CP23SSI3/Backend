package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateAddressPostDTO {
    String country;
    String postalCode;
    String city;
    String district;
    String subDistrict;
    String area;
    String latitude;
    String longitude;
}
