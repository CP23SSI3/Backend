package com.example.internhub.entities;

import com.example.internhub.entities.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "companies")
@Getter
@Setter
public class Company {
    @Id
    @Column(name = "compId", nullable = false, length = 64)
    private String id;

    @Column(name = "compName", nullable = false, length = 100)
    private String compName;

    @Column(name = "compLogoUrl", nullable = false, length = 100)
    private String compLogoUrl;

    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;

    @Column(name = "lastUpdate", nullable = false)
    private Instant lastUpdate;

    @Column(name = "lastActive", nullable = false)
    private Instant lastActive;

    @Column(name = "defaultWelfare", length = 100)
    private String defaultWelfare;

    @Column(name = "compUrl", nullable = false, length = 100)
    private String compUrl;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;
}