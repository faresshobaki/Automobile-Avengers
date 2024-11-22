package com.example.BackendAMA.controller;

import com.example.BackendAMA.dto.UserLoginRequest;
import com.example.BackendAMA.model.User;
import com.example.BackendAMA.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to create a new user
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // Endpoint to get a user by email
    @GetMapping("/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null
            ? ResponseEntity.ok(user)
            : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Endpoint to delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        return isDeleted
            ? ResponseEntity.ok("User deleted successfully")
            : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    // Endpoint for user login using UserLoginRequest
    @PostMapping("/login")
    public ResponseEntity<User> userLogin(@RequestBody UserLoginRequest loginRequest) {
        try {
            User user = userService.verifyCredentials(loginRequest.getEmail(), loginRequest.getPassword());
            return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


