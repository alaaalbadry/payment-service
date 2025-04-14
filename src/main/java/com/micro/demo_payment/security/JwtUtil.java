package com.micro.demo_payment.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {
    // Fixed Base64-encoded secret key
    private static final String SECRET_KEY_BASE64 = "0aPq1C2p6k7yMJ2rPQc3rYr+/n3qfw7qQ1rm2kVv7Tk=";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY_BASE64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate a JWT Token
    public String generateToken(String username) {
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // Add roles to the token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token valid for 1 hour
                .signWith(getSigningKey()) // Use fixed secret key for signing
                .compact();
    }

    public String generateAdminToken(String username) {
        List<String> roles = Arrays.asList("ROLE_ADMIN"); // Include both roles
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // Add both roles to the token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token valid for 1 hour
                .signWith(getSigningKey())
                .compact();
    }
    public String generateUserToken(String username) {
        List<String> roles = Arrays.asList("ROLE_USER"); // Include both roles
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles) // Add both roles to the token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Token valid for 1 hour
                .signWith(getSigningKey())
                .compact();
    }

    // Extract Roles from JWT Token
    public List<String> extractRoles(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object rolesObject = claims.get("roles"); // Extract the "roles" claim
        if (rolesObject instanceof List) {
            // Safely cast the claim to a List
            return (List<String>) rolesObject;
        }
        return new ArrayList<>(); // Return an empty list if "roles" claim is missing or null
    }

    // Extract Username from JWT Token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract Claims from JWT Token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

        Claims claims = claimsJws.getBody(); // Extract claims using getBody()
        return claimsResolver.apply(claims); // Apply the function to extract the desired claim
    }

    // Validate Token
    public boolean validateToken(String token) {
        boolean isValid = false;
        try {
            String username = extractUsername(token);
            isValid = username != null && !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid token");
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed");
        }
        return isValid;
    }

    // Check if Token Expired
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}