package com.example.internhub.dtos;

import com.example.internhub.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserPaginationObject {
    private LocalDateTime createdDate;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Role role;
    private String userId;
    private String username;
    private String userProfileKey;
}
