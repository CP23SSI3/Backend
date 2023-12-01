package com.example.internhub.entities;

import com.example.internhub.responses.Object;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class Company extends Object {

    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

    @Column(name = "compDesc", nullable = false, length = 500)
    private String compDesc;

    @Id
    @Column(name = "compId", nullable = false, length = 36)
    private String compId;

    @Column(name = "compLogoKey", nullable = false, length = 100)
    private String compLogoKey;

    @Column(name = "compName", nullable = false, length = 100)
    private String compName;

    @Column(name = "compUrl", nullable = false)
    private String compUrl;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "defaultWelfare", length = 500)
    private String defaultWelfare;

    @Column(name = "lastActive", nullable = false)
    private LocalDateTime lastActive;

    @Column(name = "lastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

    public Company(Company company, Address address){
        new Company(address, company.getCompDesc(),
                company.getCompId(), company.getCompLogoKey(),
                company.getCompName(), company.getCompUrl(),
                company.getCreatedDate(), company.getDefaultWelfare(),
                company.getLastActive(), company.getLastUpdate());
    }

}