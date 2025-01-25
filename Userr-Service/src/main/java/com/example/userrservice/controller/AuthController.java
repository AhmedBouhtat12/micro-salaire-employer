package com.example.userrservice.controller;





import com.example.userrservice.Dto.AuthRequest;
import com.example.userrservice.entities.User;
import com.example.userrservice.repository.UserRepository;
import com.example.userrservice.service.AuthService;
import com.example.userrservice.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")

public class AuthController {

@Autowired
private AuthenticationManager authenticationManager;

    private final AuthService authService;

    private final JWTService jwtService;
    @Autowired
    private UserRepository userRepository;

    public AuthController(AuthService authService, JWTService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }
    @PostMapping("/register")
    public String addNewUser(@RequestBody User user) {
        return authService.saveUser(user);
    }


    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return authService.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

//    @PostMapping("/token")
//    public ResponseEntity<Map<String, Object>> getToken(@RequestBody AuthRequest authRequest) {
//        Authentication authenticate = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//        );
//
//        if (authenticate.isAuthenticated()) {
//            // Génération du token
//            String token = authService.generateToken(authRequest.getUsername());
//
//            // Récupération de l'utilisateur connecté
//            Optional<User> user =userRepository.findByUsername(authRequest.getUsername());
//
//            // Préparer la réponse
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", token);
//            response.put("id", user.get().getId());
//            response.put("role", user.get().getRole());
//
//            return ResponseEntity.ok(response);
//        } else {
//            throw new RuntimeException("Invalid access");
//        }
//    }



    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
    @GetMapping("/role")
    public ResponseEntity<Map<String, String>> getRoleOfUser(@RequestParam("token") String token) {
        String role = authService.getTheRoleOfUser(token); // Get the role from the token
        Map<String, String> response = new HashMap<>();
        response.put("role", role); // Wrap the role in a JSON object
        return new ResponseEntity<>(response, HttpStatus.OK); // Return the JSON response
    }
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getUserProfile(@RequestParam("token") String token) {
        // Récupération des informations utilisateur à partir du jeton
        String role = authService.getTheRoleOfUser(token);
        String nom = authService.getNomFromToken(token);
        String prenom = authService.getPrenomFromToken(token);

        Map<String, Object> profile = new HashMap<>();
        profile.put("role", role);
        profile.put("nom", nom);
        profile.put("prenom", prenom);  // Vous pouvez aussi ajouter d'autres informations comme l'adresse et le téléphone

        return ResponseEntity.ok(profile);
    }



}
