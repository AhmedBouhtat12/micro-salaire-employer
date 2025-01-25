package com.example.userrservice.service;

import com.example.userrservice.entities.Role;
import com.example.userrservice.entities.User;
import com.example.userrservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private  final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(user);
        return "Saved";

    }

    public String generateToken(String username) {
        Role role = getRoleOfUser(username);
        System.out.println("Generating token for user: " + username + " with role: " + role); // Debug log
        return jwtService.generateToken(username, role);
    }

    private Role getRoleOfUser(String username) {
        return userRepository.findByUsername(username)
                .map(User::getRole)
                .orElseThrow(() -> new RuntimeException("Role not found for user: " + username));
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
    public String getTheRoleOfUser(String token) {
        return jwtService.extractRole(token);

    }
        public String getNomFromToken(String token) {
            return jwtService.extractNom(token);
        }

        public String getPrenomFromToken(String token) {
            return jwtService.extractPrenom(token);
        }
}
