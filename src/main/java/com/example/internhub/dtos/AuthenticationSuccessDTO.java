package com.example.internhub.dtos;

import com.example.internhub.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class AuthenticationSuccessDTO {
//    String accessToken;
//    String refreshToken;
    Role role;
    String userId;
    String username;
}
