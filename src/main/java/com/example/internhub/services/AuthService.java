package com.example.internhub.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.internhub.dtos.UserLoginDTO;
import com.example.internhub.entities.User;
import com.example.internhub.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public interface AuthService {
    public DecodedJWT decodeToken(String token);
    public DecodedJWT decodeBearerToken(String token);
    public String generateAccessToken(UserDetails userDetails);
    public Date generateAccessTokenExpiredDate();
    public ResponseEntity generateNewToken(HttpServletRequest req);
    public String generateRefreshToken(UserDetails userDetails);
    public Date generateRefreshTokenExpiredDate();
    public User getUserFromServletRequest(HttpServletRequest req) throws UserNotFoundException;
    public boolean isPasswordMatch(String rawPassword, User user);
    public boolean isTokenExpired(String token, UserDetails userDetails);
    public ResponseEntity logIn(UserLoginDTO userLoginDTO);
    public boolean validateToken(String token, UserDetails userDetails);
}

