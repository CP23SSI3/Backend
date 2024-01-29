package com.example.internhub.dtos;

import com.example.internhub.entities.Address;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class CreateUserDTO {
    Address address;
    String compId;
    @NotNull(message = "Email is required.")
    String email;
    @NotNull(message = "First name is required.")
    String firstname;
    @NotNull(message = "Last name is required.")
    String lastname;
    @NotNull(message = "Password is required.")
    String rawPassword;
    @NotNull(message = "User's role is required.")
    String role;
    String username;
}
