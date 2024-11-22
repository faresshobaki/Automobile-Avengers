package com.example.BackendAMA.service;

import com.example.BackendAMA.model.User;
import com.example.BackendAMA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Verifies user credentials based on email and password
    public User verifyCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    // Deletes a user by their ID
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Adds a new user to the database
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Retrieves a user by their email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}

