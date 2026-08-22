package com.example.foodservice.common.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secret;
    private final Long expiration;



    @Autowired
    public JwtService(@Value("${jwts.secret}") String secret,
                      @Value("${jwts.expiration-ms}") Long expiration) {
        this.secret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateAccessToken(Long userId) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .signWith(secret)
                .subject(userId.toString())
                .issuedAt(now)
                .expiration(expirationDate)
                .compact();
    }

    public Claims verifyAccessToken(String token) {
        return Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

    }
}
