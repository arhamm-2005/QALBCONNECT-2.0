package com.qalbconnect.qalbconnect_backend.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;  
    private String email; // Unique identifier for the user, typically used for login
    private String username;
    private String password;

    // We'll add a 'roles' field later, but for now, we'll return an empty list.
    // In a real application, you'd store the user's roles (e.g., "USER", "ADMIN")
    // and use them for authorization (e.g., allowing only admins to access certain endpoints).
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // For now, all users get ROLE_USER
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // For simplicity, we'll assume all accounts are always non-expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // For simplicity, we'll assume all accounts are always non-locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // For simplicity, we'll assume credentials are always non-expired
    }

    @Override
    public boolean isEnabled() {
        return true; // For simplicity, we'll assume all accounts are always enabled
    }
}