package com.example.internhub.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Header;
import com.example.internhub.dtos.AuthenticationSuccessDTO;
import com.example.internhub.dtos.UserLoginDTO;
import com.example.internhub.entities.User;
import com.example.internhub.responses.ResponseObject;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@Primary
public class MySQLAuthService implements AuthService{

    @Autowired
    private UserService userService;
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();
//    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());

    @Override
    public Date generateAccessTokenExpireDate() {
        return null;
    }

    @Override
    public LocalDateTime generateRefreshTokenExpiredDate() {
        return null;
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(generateAccessTokenExpireDate())
                .withClaim("role", userDetails.getAuthorities().stream().toList())
                .sign(algorithm);
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
        System.out.println(secret);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity;
        try {
            User user;
            if(userLoginDTO.getUsername() != null) user = userService.findUserByUserName(userLoginDTO.getUsername());
            else if (userLoginDTO.getEmail() != null) user = userService.findUserByEmail(userLoginDTO.getEmail());
            else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with inputed data is not existed.");
            UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
//            System.out.println(isPasswordMatch(userLoginDTO.getPassword(), user));
            AuthenticationSuccessDTO authenticationSuccessDTO = new AuthenticationSuccessDTO(
                    generateAccessToken(userDetails),
//                    null,
                    null, user.getRole(), user.getUserId(), user.getUsername());
            responseEntity = new ResponseEntity(authenticationSuccessDTO, headers, HttpStatus.OK);
        } catch (Exception ex) {
            ResponseObject responseObject = new ResponseObject(400, ex.getMessage(), null);
            responseEntity = new ResponseEntity(responseObject, headers, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, User user) {
        return encoder.matches(rawPassword, user.getPassword());
    }
}
