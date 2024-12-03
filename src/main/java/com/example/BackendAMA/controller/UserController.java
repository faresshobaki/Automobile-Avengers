package com.example.BackendAMA.controller;

import com.example.BackendAMA.dto.UserLoginRequest;
import com.example.BackendAMA.model.User;
import com.example.BackendAMA.service.UserService;
import com.example.BackendAMA.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    
    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserLoginRequest loginRequest) {
        System.out.println("Login request received for: " + loginRequest.getEmail());
        User user = userService.verifyCredentials(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            String token = JwtUtil.generateToken(user.getEmail()); 
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("firstName", user.getFirstName());
            System.out.println("Login successful for: " + loginRequest.getEmail());
            return ResponseEntity.ok(response);
        } else {
            System.out.println("Login failed for: " + loginRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    
    @GetMapping("/validate-session")
    public ResponseEntity<String> validateSession(@RequestHeader("Authorization") String token) {
        boolean isValid = userService.validateSession(token);
        if (isValid) {
            String username = JwtUtil.extractUsername(token);
            return ResponseEntity.ok("Valid session for " + username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid session");
        }
    }
}




