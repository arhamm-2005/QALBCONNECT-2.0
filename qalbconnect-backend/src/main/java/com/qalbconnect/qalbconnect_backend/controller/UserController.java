package com.qalbconnect.qalbconnect_backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qalbconnect.qalbconnect_backend.model.User; // New import
import com.qalbconnect.qalbconnect_backend.security.JwtUtil;
import com.qalbconnect.qalbconnect_backend.service.UserDetailsServiceImpl;
import com.qalbconnect.qalbconnect_backend.service.UserService; // New import: Your JWT Utility class


// Remove these imports as Jwts, SignatureAlgorithm, and Keys are no longer directly used for token building here
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil; // Inject JwtUtil to generate tokens

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // Inject UserDetailsServiceImpl to load UserDetails

    // Remove the JWT_SECRET generation here. The secret is now managed by JwtUtil
    // private final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

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
            String username = loggedInUser.get().getUsername();
            String userId = loggedInUser.get().getId();

            // Load UserDetails from your UserDetailsService
            // This is crucial to ensure consistency with JwtRequestFilter's validation
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Use JwtUtil to generate the token
            String token = jwtUtil.generateToken(userDetails);

            response.put("message", "Login successful!");
            response.put("username", username);
            response.put("userId", userId);
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Login failed. Invalid username or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}