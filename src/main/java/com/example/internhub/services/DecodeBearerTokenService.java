package com.example.internhub.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.internhub.security.AuthTokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DecodeBearerTokenService {

    private String secret = "internhub.secret";

//    @Value("internhub.secret")
//    private String try;

    public DecodedJWT decodeBearerToken(String token) {
        String bearer = new AuthTokenType().BEARER;
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.require(algorithm).build().verify(token.replace(bearer, ""));
    }
}
