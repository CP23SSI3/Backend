package com.example.internhub.dtos;

import com.example.internhub.validators.EnumGenderTypeConstraint;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class EditUserDTO {
    @Valid
    @NotNull(message = "Address is required.")
    EditAddressDTO address;
    @NotNull(message = "Date of birth is required.")
    @Past(message = "Input birthdate is in the future.")
    LocalDate dateOfBirth;
    @NotNull(message = "First name is required.")
    @Size(max = 50, message = "User's firstname is too long, 50 characters maximum.")
    String firstname;
    @EnumGenderTypeConstraint
    String gender;
    LocalDateTime lastActive = LocalDateTime.now();
    @NotNull(message = "Last name is required.")
    @Size(max = 50, message = "User's lastname is too long, 50 characters maximum.")
    String lastname;
    LocalDateTime lastUpdate = LocalDateTime.now();
    @NotNull(message = "Phone number is required.")
    @Size(max = 10, message = "User's phone number is too long, 10 characters maximum.")
    String phoneNumber;
    @Size(max = 1500, message = "User's description is too long. 1500 characters maximum.")
    String userDesc;
    @NotNull(message = "Username is required.")
    @Size(max = 50, message = "User's username is too long, 50 characters maximum.")
    String username;
}
