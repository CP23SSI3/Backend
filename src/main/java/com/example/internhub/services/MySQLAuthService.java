package com.example.internhub.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.internhub.dtos.AuthenticationSuccessDTO;
import com.example.internhub.dtos.UserLoginDTO;
import com.example.internhub.entities.User;
import com.example.internhub.responses.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@Primary
public class MySQLAuthService implements AuthService{

    @Autowired
    private UserService userService;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${access.token.expired}")
    private Long accessTokenMilliSecondsBeforeExpired;
    @Value("${refresh.token.expired}")
    private Long refreshTokenMilliSecondsBeforeExpired;

    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();
//    Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());

    @Override
    public Date generateAccessTokenExpiredDate() {
        return new Date(System.currentTimeMillis() + accessTokenMilliSecondsBeforeExpired);
    }

    @Override
    public Date generateRefreshTokenExpiredDate() {
        return new Date(System.currentTimeMillis() + refreshTokenMilliSecondsBeforeExpired);
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(generateAccessTokenExpiredDate())
                .withClaim("role", userDetails.getAuthorities().stream().toList().get(0).toString())
                .sign(algorithm);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(generateRefreshTokenExpiredDate())
                .withClaim("role", userDetails.getAuthorities().stream().toList().get(0).toString())
                .sign(algorithm);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        DecodedJWT decodeJwt = JWT.require(algorithm).build().verify(token);
        return decodeJwt.getSubject().equals(userDetails.getUsername()) &&
                !isTokenExpired(token, userDetails);
    }

    @Override
    public boolean isTokenExpired(String token, UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        DecodedJWT decodeJwt = JWT.require(algorithm).build().verify(token);
        return decodeJwt.getExpiresAt().before(new Date(System.currentTimeMillis()));
    }

    @Override
    public ResponseEntity logIn(UserLoginDTO userLoginDTO) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity;
        try {
            User user;
            if(userLoginDTO.getUsername() != null) user = userService.findUserByUserName(userLoginDTO.getUsername());
            else if (userLoginDTO.getEmail() != null) user = userService.findUserByEmail(userLoginDTO.getEmail());
            else throw new UsernameNotFoundException("User with inputed data is not existed.");
            boolean isPasswordMatches = userService.isPasswordMatch(userLoginDTO.getPassword(), user.getPassword());
            if(!isPasswordMatches) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is incorrect");
            UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
            AuthenticationSuccessDTO authenticationSuccessDTO = new AuthenticationSuccessDTO(
                    generateAccessToken(userDetails),
                    generateRefreshToken(userDetails),
                    user.getRole(), user.getUserId(), user.getUsername());
            responseEntity = new ResponseEntity(authenticationSuccessDTO, headers, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            ResponseObject responseObject = new ResponseObject(ex.getStatus().value(), ex.getMessage(), null);
            System.out.println("response exception");
            responseEntity = new ResponseEntity(responseObject, headers, ex.getStatus());
        } catch(UsernameNotFoundException ex) {
            ResponseObject responseObject = new ResponseObject(404, ex.getMessage(), null);
            responseEntity = new ResponseEntity(responseObject, headers, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ResponseObject responseObject = new ResponseObject(400, ex.getMessage(), null);
            System.out.println("exception");
            responseEntity = new ResponseEntity(responseObject, headers, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, User user) {
        return encoder.matches(rawPassword, user.getPassword());
    }
}
