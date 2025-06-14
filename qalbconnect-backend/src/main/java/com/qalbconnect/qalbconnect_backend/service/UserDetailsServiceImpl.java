package com.qalbconnect.qalbconnect_backend.service; // Or com.qalbconnect.qalbconnect_backend.security.service;

import com.qalbconnect.qalbconnect_backend.model.User;
import com.qalbconnect.qalbconnect_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Mark this class as a Spring Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Inject your UserRepository

    /**
     * This method is called by Spring Security to load user details for authentication.
     * It fetches the User from your MongoDB and returns it as a Spring Security UserDetails object.
     * @param username The username provided during authentication.
     * @return UserDetails object containing user's username, password, and authorities.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return userOptional.get(); // Since your User entity implements UserDetails, we can return it directly.
    }
}