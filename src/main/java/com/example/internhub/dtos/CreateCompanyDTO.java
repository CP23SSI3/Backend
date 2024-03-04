package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
public class CreateCompanyDTO {
    String compId = UUID.randomUUID().toString();
    @NotNull(message = "Company's name is required.")
    @Size(max = 100, message = "Company's name is too long, 100 characters maximum.")
    String compName;
    @Size(max = 5000, message = "Company's logo is too long, 5000 characters maximum.")
    @NotNull(message = "Company's logo is required.")
    String compLogoKey;
    LocalDateTime createdDate = LocalDateTime.now();
    LocalDateTime lastActive = LocalDateTime.now();
    LocalDateTime lastUpdate = LocalDateTime.now();
}
