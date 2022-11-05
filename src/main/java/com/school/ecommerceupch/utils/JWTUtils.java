package com.school.ecommerceupch.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {

    @Value("${ACCESS_TOKEN_SECRET}")
    private String ACCESS_TOKEN_SECRET;
    private final Long ACCESS_TOKEN_VALIDATION_SECONDS = 2_592_000L;

    public String generateToken(String email) {
        Long expirationTime = ACCESS_TOKEN_VALIDATION_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public Boolean isValidateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parseClaimsJws(token);
            return Boolean.TRUE;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
