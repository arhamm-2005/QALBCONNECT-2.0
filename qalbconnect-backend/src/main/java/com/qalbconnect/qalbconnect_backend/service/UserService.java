package com.qalbconnect.qalbconnect_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder; // NEW IMPORT
import org.springframework.stereotype.Service;

import com.qalbconnect.qalbconnect_backend.model.User;
import com.qalbconnect.qalbconnect_backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder; // NEW: Inject PasswordEncoder

    /**
     * Registers a new user after checking for unique username and email.
     * Hashes the password before saving.
     * @param user The User object to register (with plain text password).
     * @return An Optional containing the saved User if registration is successful,
     * or empty if username/email already exists.
     */
    public Optional<User> registerUser(User user) {
        // Check if username already exists
        Query queryByUsername = new Query(Criteria.where("username").is(user.getUsername()));
        if (mongoTemplate.exists(queryByUsername, User.class)) {
            return Optional.empty(); // Username already exists
        }

        // Check if email already exists
        Query queryByEmail = new Query(Criteria.where("email").is(user.getEmail()));
        if (mongoTemplate.exists(queryByEmail, User.class)) {
            return Optional.empty(); // Email already exists
        }

        // NEW: Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user using UserRepository
        User savedUser = userRepository.save(user);
        return Optional.of(savedUser);
    }

    /**
     * NOTE: This loginUser method will become less relevant for primary authentication
     * once Spring Security is fully integrated, as Spring Security will handle
     * the password comparison automatically. However, you might keep it for other
     * internal logic if needed, but its role in authentication flow will change.
     * For now, we'll keep it but acknowledge its changing role.
     *
     * Handles user login by checking username and password.
     * @param username The username provided by the user.
     * @param password The plain text password provided by the user.
     * @return An Optional containing the User if login is successful, or empty otherwise.
     */
    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // NEW: Use passwordEncoder to match the provided password with the stored HASHED password
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user); // Password matches
            }
        }
        return Optional.empty(); // User not found or password does not match
    }

    // You can keep other methods like findUserById, deleteUser, etc., as they are.
    // ... (rest of your UserService methods) ...

     public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean deleteUser(String id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }
}