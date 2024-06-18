package com.portfolio.boardproject.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.portfolio.boardproject.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${token.secret}")
    private String tokenSecret;

    private Long tokenExpiration = 1000L * 60 * 60;


    public String createToken(CustomUserDetails user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenExpiration);

        return JWT.create()
                .withIssuedAt(now)
                .withSubject(user.getUsername())
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC256(tokenSecret));
    }

    public Boolean verifyToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenSecret)).build();
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getUsername(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenSecret)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getSubject();
    }
}
