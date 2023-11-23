package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
public class CreateAddressDTO {
    String addressId = UUID.randomUUID().toString();
    String country;
    String postalCode;
    String city;
    String district;
    String subDistrict;
    String area;
    Double latitude;
    Double longitude;

    public BigDecimal getLatitude() {
        return BigDecimal.valueOf(latitude).setScale(8);
    }

    public BigDecimal getLongitude() {
        return BigDecimal.valueOf(longitude).setScale(8);
    }
}
