package com.example.internhub.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.internhub.dtos.UserLoginDTO;
import com.example.internhub.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface AuthService {
    public DecodedJWT decodeToken(String token);
    public String generateAccessToken(UserDetails userDetails);
    public Date generateAccessTokenExpiredDate();
    public String generateRefreshToken(UserDetails userDetails);
    public Date generateRefreshTokenExpiredDate();
    public boolean isPasswordMatch(String rawPassword, User user);
    public boolean isTokenExpired(String token, UserDetails userDetails);
    public ResponseEntity logIn(UserLoginDTO userLoginDTO);
    public boolean validateToken(String token, UserDetails userDetails);
}

