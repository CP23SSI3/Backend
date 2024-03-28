package com.example.internhub.dtos;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
public class EditCompanyDTO {
    @Valid
    public EditAddressDTO address;
    @Size(max = 500, message = "Company's description is too long, 500 characters maximum.")
    String compDesc;
    @NotNull(message = "Company's logo is required.")
    @Size(max = 5000, message = "Company's logo is too long, 5000 characters maximum.")
    String compLogoKey;
    @NotNull(message = "Company's name is required.")
    @Size(max = 100, message = "Company's name is too long, 100 characters maximum.")
    String compName;
    @Size(max = 255, message = "Company's url is too long, 255 characters maximum.")
    String compUrl;
    @Size(max = 1500, message = "Company's default welfare is too long, 1500 characters maximum.")
    String defaultWelfare;
    LocalDateTime lastActive = LocalDateTime.now();
    LocalDateTime lastUpdate = LocalDateTime.now();
}
