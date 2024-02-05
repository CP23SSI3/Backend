package com.example.internhub.dtos;

import com.example.internhub.validators.EnumRoleConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter @Setter
public class CreateUserDTO {
    CreateAddressDTO address;
    String compId;
    LocalDateTime createdDate = LocalDateTime.now();
    @NotNull(message = "Email is required.")
    @Size(max = 320, message = "Email is too long, 320 characters maximum.")
    String email;
    @NotNull(message = "First name is required.")
    @Size(max = 50, message = "User's firstname is too long, 50 characters maximum.")
    String firstname;
    LocalDateTime lastActive = LocalDateTime.now();
    @NotNull(message = "Last name is required.")
    @Size(max = 50, message = "User's lastname is too long, 50 characters maximum.")
    String lastname;
    LocalDateTime lastUpdate = LocalDateTime.now();
    @NotNull(message = "Phone number is required.")
    @Size(max = 10, message = "User's phone number is too long, 10 characters maximum.")
    String phoneNumber;
    @NotNull(message = "Password is required.")
    String rawPassword;
    @NotNull(message = "Role is required.")
    @EnumRoleConstraint
    String role;
    String userId = UUID.randomUUID().toString();
    @NotNull(message = "Username is required.")
    @Size(max = 50, message = "User's username is too long, 50 characters maximum.")
    String username;
}
