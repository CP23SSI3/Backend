package com.example.internhub.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Header;
import com.example.internhub.dtos.AuthenticationSuccessDTO;
import com.example.internhub.dtos.UserLoginDTO;
import com.example.internhub.entities.User;
import com.example.internhub.responses.ResponseObject;
import com.example.internhub.security.AuthTokenType;
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

import javax.servlet.http.HttpServletRequest;
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
    public DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.require(algorithm).build().verify(token);
    }

    public DecodedJWT decodeBearerToken(String token) {
        String bearer = new AuthTokenType().BEARER;
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.require(algorithm).build().verify(token.replace(bearer, ""));
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
    public Date generateAccessTokenExpiredDate() {
        return new Date(System.currentTimeMillis() + accessTokenMilliSecondsBeforeExpired);
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
    public Date generateRefreshTokenExpiredDate() {
        return new Date(System.currentTimeMillis() + refreshTokenMilliSecondsBeforeExpired);
    }

    @Override
    public ResponseEntity generateNewToken(HttpServletRequest req) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity;
        String refreshToken = req.getHeader("refresh-token");
        DecodedJWT jwt = decodeBearerToken(refreshToken);
        String username = jwt.getSubject();
        try {
            User user = null;
            if (username != null) {
                user = userService.findUserByUsernameOrEmail(username);
            }
            if (user == null) throw new UsernameNotFoundException("User with input data is not existed.");
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            AuthenticationSuccessDTO authenticationSuccessDTO = new AuthenticationSuccessDTO(
                    user.getRole(), user.getUserId(), user.getUsername());
            headers.add("access-token", generateAccessToken(userDetails));
            headers.add("refresh-token", generateRefreshToken(userDetails));
            responseEntity = new ResponseEntity(new ResponseObject(200, "New token is successfully send.", authenticationSuccessDTO),
                    headers, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            ResponseObject responseObject = new ResponseObject(ex.getStatus().value(), ex.getMessage(), null);
            responseEntity = new ResponseEntity(responseObject, headers, ex.getStatus());
        } catch(UsernameNotFoundException ex) {
            ResponseObject responseObject = new ResponseObject(404, ex.getMessage(), null);
            responseEntity = new ResponseEntity(responseObject, headers, HttpStatus.NOT_FOUND);
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

    @Override
    public boolean isTokenExpired(String token, UserDetails userDetails) {
        DecodedJWT decodeJwt = decodeToken(token);
        return decodeJwt.getExpiresAt().before(new Date(System.currentTimeMillis()));
    }

    @Override
    public ResponseEntity logIn(UserLoginDTO userLoginDTO) {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity responseEntity;
        UserDetails userDetails;
        try {
            User user = null;
            if (userLoginDTO.getUsername() != null) {
                user = userService.findUserByUsernameOrEmail(userLoginDTO.getUsername());
            }
            if (user == null) throw new UsernameNotFoundException("User with input data is not existed.");
            boolean isPasswordMatches = userService.isPasswordMatch(userLoginDTO.getPassword(), user.getPassword());
            if(!isPasswordMatches) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is incorrect");
            userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            AuthenticationSuccessDTO authenticationSuccessDTO = new AuthenticationSuccessDTO(
                    user.getRole(), user.getUserId(), user.getUsername());
            headers.add("access-token", generateAccessToken(userDetails));
            headers.add("refresh-token", generateRefreshToken(userDetails));
            responseEntity = new ResponseEntity(new ResponseObject(200, "Log in successful.", authenticationSuccessDTO)
                    , headers, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            ResponseObject responseObject = new ResponseObject(ex.getStatus().value(), ex.getMessage(), null);
            responseEntity = new ResponseEntity(responseObject, headers, ex.getStatus());
        } catch(UsernameNotFoundException ex) {
            ResponseObject responseObject = new ResponseObject(404, ex.getMessage(), null);
            responseEntity = new ResponseEntity(responseObject, headers, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            ResponseObject responseObject = new ResponseObject(400, ex.getMessage(), null);
            responseEntity = new ResponseEntity(responseObject, headers, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        DecodedJWT decodeJwt = decodeToken(token);
        return decodeJwt.getSubject().equals(userDetails.getUsername()) &&
                !isTokenExpired(token, userDetails);
    }

}
