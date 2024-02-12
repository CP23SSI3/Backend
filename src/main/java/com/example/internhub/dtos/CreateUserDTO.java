package com.example.internhub.dtos;

import com.example.internhub.validators.EnumGenderTypeConstraint;
import com.example.internhub.validators.EnumRoleConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter @Setter
public class CreateUserDTO {
    String compId;
    LocalDateTime createdDate = LocalDateTime.now();
    @NotNull(message = "Date of birth is required.")
    @Past(message = "Input birthdate is in the future.")
    LocalDate dateOfBirth;
    @NotNull(message = "Email is required.")
    @Size(max = 320, message = "Email is too long, 320 characters maximum.")
    @Email
    String email;
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
    @NotNull(message = "Password is required.")
    String rawPassword;
    @NotNull(message = "Role is required.")
    @EnumRoleConstraint
    String role;
    @Size(max = 1500, message = "User's description is too long. 1500 characters maximum.")
    String userDesc;
    String userId = UUID.randomUUID().toString();
    @NotNull(message = "Username is required.")
    @Size(max = 50, message = "User's username is too long, 50 characters maximum.")
    String username;
    @Size(max = 100, message = "User's profile key is too long. 100 characters maximum.")
    String userProfileKey;
}
