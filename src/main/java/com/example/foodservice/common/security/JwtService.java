package com.example.foodservice.common.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
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

    protected static final String SUBJECT_TYPE_KEY = "subjectType";
    protected static  final String BRAND_ID_KEY = "brandId";

    @Autowired
    public JwtService(@Value("${jwts.secret}") String secret,
                      @Value("${jwts.expiration-ms}") Long expiration) {
        this.secret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    public String generateAccessToken(Long id, SubjectType subjectType) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                    .signWith(secret)
                    .subject(id.toString())
                    .claim(SUBJECT_TYPE_KEY, subjectType)
                    .issuedAt(now)
                    .expiration(expirationDate)
                    .compact();
    }

    public String generateAccessToken(Long id, Long brandId, SubjectType subjectType) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .signWith(secret)
                .subject(id.toString())
                .claim(SUBJECT_TYPE_KEY, subjectType)
                .claim(BRAND_ID_KEY, brandId)
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
