package com.school.ecommerceupch.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.Date;

public class JWTUtils {

    @Value("{ACCESS_TOKEN_SECRET}")
    private static String ACCESS_TOKEN_SECRET;
    private final static Long ACCESS_TOKEN_VALIDATION_SECONDS = 2_592_000L;

    public static String generateToken(String email) {
        Long expirationTime = ACCESS_TOKEN_VALIDATION_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static Boolean isValidateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parseClaimsJws(token);
            return Boolean.TRUE;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    public static String getEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build()
                .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
