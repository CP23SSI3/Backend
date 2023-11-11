package com.example.internhub.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Company {
    @Id
    @Column(name = "compId", nullable = false, length = 36)
    private String compId;

    @Column(name = "compName", nullable = false, length = 100)
    private String compName;

    @Column(name = "compLogoKey", nullable = false, length = 100)
    private String compLogoKey;

    @Column(name = "compDesc", nullable = false, length = 500)
    private String compDesc;

    @Column(name = "defaultWelfare", length = 500)
    private String defaultWelfare;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "lastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

    @Column(name = "lastActive", nullable = false)
    private LocalDateTime lastActive;

    @Column(name = "compUrl", nullable = false)
    private String compUrl;

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

//    public Company(Company company){
//        new Company(company.getCompId(), company.getCompName(),
//                company.getCompLogoKey(), company.getCompDesc(),
//                company.getDefaultWelfare(), company.getCreatedDate(),
//                company.getLastUpdate(), company.getLastActive(),
//                company.getCompUrl(), new Address(company.getAddress()));
//    }

}