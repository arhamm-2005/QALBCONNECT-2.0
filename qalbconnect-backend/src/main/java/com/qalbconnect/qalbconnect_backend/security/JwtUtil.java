package com.qalbconnect.qalbconnect_backend.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component // Mark as a Spring component for dependency injection
public class JwtUtil {

    // Secret key for signing JWTs. Store this securely in application.properties!
    // Never expose your secret key directly in code in production.
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Token validity in milliseconds (e.g., 10 hours)
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME; // e.g., 1000 * 60 * 60 * 10 (10 hours)

    // --- Helper methods for token parsing ---

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // --- Token Generation ---

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // You can add additional claims here if needed (e.g., user roles, user ID)
        // If your User model has an 'id' field and you want it in the token,
        // you can cast userDetails back to your User class if User implements UserDetails
        // For example:
        // if (userDetails instanceof com.qalbconnect.qalbconnect_backend.model.User) {
        //     claims.put("userId", ((com.qalbconnect.qalbconnect_backend.model.User) userDetails).getId());
        // }

        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // The username is typically the subject of the token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token validity
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign with your secret key
                .compact();
    }

    // --- Token Validation ---

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // --- Secret Key Management ---

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}