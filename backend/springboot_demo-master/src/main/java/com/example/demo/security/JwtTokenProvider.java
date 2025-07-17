package com.example.demo.security;

import java.sql.Date;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

    public String generateTokenFromUsername(String username) {
         
        byte[] secretKeyBytes = Base64.getEncoder().encode(SecurityConstants.getTockenSecrect().getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        Instant now = Instant.now();
        String token = Jwts.builder().setSubject(username)
                .setExpiration(Date.from(now.plusMillis(SecurityConstants.EXPIRATION_TIME))).setIssuedAt(Date.from(now))
                .signWith(secretKey, SignatureAlgorithm.HS512).compact();
        
        return token;
        
    }
}