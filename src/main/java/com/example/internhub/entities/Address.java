package com.example.internhub.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "addresses")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Address {
    @Id
    @Column(name = "addressId", nullable = false, length = 36)
    private String addressId;

    @Column(name = "area", nullable = false, length = 100)
    private String area;

    @Column(name = "city", nullable = false, length = 35)
    private String city;

    @Column(name = "country", nullable = false, length = 90)
    private String country;

    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "postalCode", nullable = false, length = 10)
    private String postalCode;

    @Column(name = "subDistrict", nullable = false, length = 50)
    private String subDistrict;

    public Address(Address address) {
        new Address(address.getAddressId(), address.getArea(),
                address.getCity(), address.getCountry(),
                address.getDistrict(), address.getLatitude(), address.getLongitude(),
                address.getPostalCode(),
                address.getSubDistrict());
    }

}