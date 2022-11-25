package com.school.ecommerceupch.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {

    //    @Value("${ACCESS_TOKEN_SECRET}")
    private final String ACCESS_TOKEN_SECRET = "b0f7247ad9dbff2e3b51e60756a8f177703e6fd45df858581801f688ed6c5e21";
    private final Long ACCESS_TOKEN_VALIDATION_SECONDS = 2_592_000L;

    public String generateToken(Map<String, Object> payload) {
        Long expirationTime = ACCESS_TOKEN_VALIDATION_SECONDS * 1_000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        return Jwts.builder()
                .setExpiration(expirationDate)
                .addClaims(payload)
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
