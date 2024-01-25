package com.example.internhub.services;

import com.example.internhub.dtos.UserLoginDTO;
import com.example.internhub.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public interface AuthService {
    public Date generateAccessTokenExpireDate();
    public LocalDateTime generateRefreshTokenExpiredDate();
    public String generateAccessToken(UserDetails userDetails);
    public String generateRefreshToken(UserDetails userDetails);
    public boolean validateToken(String token, UserDetails userDetails);
    public boolean isTokenExpired(String token, UserDetails userDetails);
    public ResponseEntity logIn(UserLoginDTO userLoginDTO);
    public boolean isPasswordMatch(String rawPassword, User user);
}

