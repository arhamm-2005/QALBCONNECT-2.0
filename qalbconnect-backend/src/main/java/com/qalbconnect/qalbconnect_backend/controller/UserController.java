package com.qalbconnect.qalbconnect_backend.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Removed CrossOrigin
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qalbconnect.qalbconnect_backend.model.User;
import com.qalbconnect.qalbconnect_backend.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api/auth")
// Removed @CrossOrigin(origins = "*") because it's handled by CorsConfig.java
public class UserController {

    @Autowired
    private UserService userService;

    // A secret key for JWT signing. In a production environment, this should be
    // loaded from a secure configuration (e.g., environment variable, Vault)
    // and should be at least 256 bits (32 bytes) for HS256.
    // For demonstration, a strong random key is generated.
    private final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Handles user registration.
     * Endpoint: POST /api/auth/register
     * Body: { "username": "...", "email": "...", "password": "..." }
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        Optional<User> registeredUser = userService.registerUser(user);

        if (registeredUser.isPresent()) {
            response.put("message", "Registration successful!");
            response.put("username", registeredUser.get().getUsername());
            response.put("userId", registeredUser.get().getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            response.put("message", "Registration failed. Username or email already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Handles user login.
     * Endpoint: POST /api/auth/login
     * Body: { "username": "...", "password": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        Optional<User> loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());

        if (loggedInUser.isPresent()) {
            // User successfully authenticated, now generate JWT
            String username = loggedInUser.get().getUsername();
            String userId = loggedInUser.get().getId();

            // Set JWT expiration time (e.g., 1 hour)
            long expirationTimeMillis = System.currentTimeMillis() + 3600 * 1000; // 1 hour
            Date expirationDate = new Date(expirationTimeMillis);

            String token = Jwts.builder()
                    .setSubject(username)
                    .setId(userId)
                    .setIssuedAt(new Date())
                    .setExpiration(expirationDate)
                    .signWith(JWT_SECRET, SignatureAlgorithm.HS256)
                    .compact();

            response.put("message", "Login successful!");
            response.put("username", username);
            response.put("userId", userId);
            response.put("token", token); // <-- This is the crucial part: returning the token
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Login failed. Invalid username or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}