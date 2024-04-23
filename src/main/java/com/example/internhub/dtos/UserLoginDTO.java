package com.example.internhub.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class UserLoginDTO {
    @NotNull(message = "Username or email is required for logging in.")
    @Size(max = 320, message = "Username or email is too long, 320 characters maximum.")
    private String username;
    @NotNull(message = "Password is required for logging in.")
    private String password;
}
