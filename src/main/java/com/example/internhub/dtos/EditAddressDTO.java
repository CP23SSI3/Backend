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
public class EditAddressDTO {
    @NotNull(message = "Address's area is required.")
    @Size(max = 135, message = "Address's area is too long, 135 characters maximum.")
    String area;
    @NotNull(message = "City's is required.")
    @Size(max = 35, message = "City's name is too long, 35 characters maximum.")
    String city;
    @NotNull(message = "Country's name is required.")
    @Size(max = 90, message = "Country's name is too long, 90 characters maximum.")
    String country;
    @NotNull(message = "District's name is required.")
    @Size(max = 50, message = "District's name is too long, 50 characters maximum.")
    String district;
    @NotNull(message = "Latitude is required.")
    @DecimalMax(value = "180", message = "Latitude is out of length.")
    @DecimalMin(value = "-180", message = "Latitude is out of length.")
    Double latitude;
    @NotNull(message = "Longitude is required.")
    @DecimalMax(value = "90", message = "Longitude is out of length.")
    @DecimalMin(value = "-90", message = "Longitude is out of length.")
    Double longitude;
    @NotNull(message = "Postal code is required.")
    @Size(max = 10, message = "Postal code is too long, 10 characters maximum.")
    String postalCode;
    @NotNull(message = "Sub district is required.")
    @Size(max = 50, message = "Sub district is too long, 50 characters maximum.")
    String subDistrict;

    public BigDecimal getLatitude() {
        return BigDecimal.valueOf(latitude).setScale(8);
    }

    public BigDecimal getLongitude() {
        return BigDecimal.valueOf(longitude).setScale(8);
    }

}
