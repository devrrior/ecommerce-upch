package com.school.ecommerceupch.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

import java.util.Date;

public class JWTUtils {

    private final static String ACCESS_TOKEN_SECRET = "b0f7247ad9dbff2e3b51e60756a8f177703e6fd45df858581801f688ed6c5e21";
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
