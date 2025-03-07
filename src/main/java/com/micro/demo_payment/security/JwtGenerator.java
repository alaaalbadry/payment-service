package com.micro.demo_payment.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtGenerator {
    public static void main(String[] args) {
        // Generate a secure 256-bit key
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Print the key in Base64 format (use this key in your application)
        String secretKey = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated Secret Key: " + secretKey);

        // Generate JWT
        String token = Jwts.builder()
                .setSubject("admin")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Expires in 1 hour
                .signWith(key)
                .compact();

        System.out.println("Generated Token: " + token);
    }
}
