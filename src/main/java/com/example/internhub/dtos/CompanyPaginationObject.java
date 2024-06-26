package com.example.internhub.dtos;

import com.example.internhub.entities.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CompanyPaginationObject {
    private String compId;
    private String compLogoKey;
    private String compName;
}
