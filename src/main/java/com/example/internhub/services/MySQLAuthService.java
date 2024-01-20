package com.example.internhub.services;

import com.auth0.jwt.interfaces.Header;
import com.example.internhub.dtos.UserLoginDTO;
import com.example.internhub.entities.User;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@Primary
public class MySQLAuthService implements AuthService{

    @Autowired
    private UserService userService;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public LocalDateTime generateAccessTokenExpireDate() {
        return null;
    }

    @Override
    public LocalDateTime generateRefreshTokenExpiredDate() {
        return null;
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return null;
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return null;
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public boolean isTokenExpired(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public ResponseEntity logIn(UserLoginDTO userLoginDTO) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity;
        try {
            User user;
            if(userLoginDTO.getUsername() != null) user = userService.findUserByUserName(userLoginDTO.getUsername());
            else if (userLoginDTO.getEmail() != null) user = userService.findUserByEmail(userLoginDTO.getEmail());
            else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with inputed data is not existed.");
            System.out.println(isPasswordMatch(userLoginDTO.getPassword(), user));
            responseEntity = new ResponseEntity(new User(), headers, HttpStatus.OK);
        } catch (Exception ex) {
            responseEntity = new ResponseEntity(new User(), headers, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, User user) {
        return encoder.matches(rawPassword, user.getPassword());
    }
}
