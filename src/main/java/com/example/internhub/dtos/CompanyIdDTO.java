package com.example.internhub.dtos;

import com.example.internhub.entities.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CompanyIdDTO {
    @NotNull(message = "Company's id is required.")
    String compId;
}
