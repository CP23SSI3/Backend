package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UserLoginDTO {
    private String email;
    private String username;
    @NotNull(message = "Password is required for logging in.")
    private String password;
}
