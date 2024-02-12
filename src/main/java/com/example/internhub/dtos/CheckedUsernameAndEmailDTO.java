package com.example.internhub.dtos;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class CheckedUsernameAndEmailDTO {
    @NotNull(message = "Username is required.")
    public String username;
    @NotNull(message = "User's email is required.")
    @Email(message = "Unknown email format.")
    public String email;
}
