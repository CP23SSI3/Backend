package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
public class CreateAddressDTO {
    String addressId = UUID.randomUUID().toString();
    @NotNull @Size(max = 135)
    String area;
    @NotNull @Size(max = 35)
    String city;
    @NotNull @Size(max = 90)
    String country;
    @NotNull @Size(max = 50)
    String district;
    @NotNull @DecimalMax(value = "180")  @DecimalMin(value = "-180")
    Double latitude;
    @NotNull @DecimalMax(value = "90") @DecimalMin(value = "-90")
    Double longitude;
    @NotNull @Size(max = 10)
    String postalCode;
    @NotNull @Size(max = 50)
    String subDistrict;

    public BigDecimal getLatitude() {
        return BigDecimal.valueOf(latitude).setScale(8);
    }

    public BigDecimal getLongitude() {
        return BigDecimal.valueOf(longitude).setScale(8);
    }
}
