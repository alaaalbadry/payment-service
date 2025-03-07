package com.micro.demo_payment.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET_KEY_BASE64 = "nR85cV0ML0GvhuQsW/cq570c/sBozav84C6v3CIUJPA=";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY_BASE64);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    // Generate a JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) // Add username as subject
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token valid for 1 hour
                .signWith(getSigningKey()) // Signing with key
                .compact();
    }

    // Extract Username from JWT Token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Claims from JWT Token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

        Claims claims = claimsJws.getPayload(); // Extract Claims
        return claimsResolver.apply(claims);
    }

    // Validate Token
    public boolean validateToken(String token, String username) {
        return (extractUsername(token).equals(username)) && !isTokenExpired(token);
    }

    // Check if Token Expired
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
