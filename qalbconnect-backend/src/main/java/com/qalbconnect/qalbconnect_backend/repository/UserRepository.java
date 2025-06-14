package com.qalbconnect.qalbconnect_backend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qalbconnect.qalbconnect_backend.model.User;

// By extending MongoRepository, we get basic CRUD operations for free
public interface UserRepository extends MongoRepository<User, String> {

    // Custom query method to find a User by username
    // Spring Data will automatically implement this as a find operation on the 'username' field
    Optional<User> findByUsername(String username);

    // Custom query method to find a User by email
    // Spring Data will automatically implement this as a find operation on the 'email' field
    Optional<User> findByEmail(String email);
}