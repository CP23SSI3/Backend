package com.example.internhub.entities;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
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
    private Instant createdDate;

    @Column(name = "lastUpdate", nullable = false)
    private Instant lastUpdate;

    @Column(name = "lastActive", nullable = false)
    private Instant lastActive;

    @Column(name = "compUrl", nullable = false)
    private String compUrl;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

}