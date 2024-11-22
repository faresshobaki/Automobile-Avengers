package com.example.BackendAMA.service;

import com.example.BackendAMA.model.User;
import com.example.BackendAMA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, String> verificationCodes = new HashMap<>();

    // Verifies user credentials
    public User verifyCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    // Creates a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Retrieves a user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Deletes a user by ID
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Sends a verification code to the user's email
    public boolean sendVerificationCode(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String verificationCode = generateVerificationCode();
            verificationCodes.put(email, verificationCode);

            // Send the verification code via email
            sendEmail(email, "Password Reset Verification Code", "Your verification code is: " + verificationCode);

            return true;
        }
        return false;
    }

    // Resets the user's password after verifying the code
    public boolean resetPassword(String email, String verificationCode, String newPassword) {
        if (verificationCodes.containsKey(email) && verificationCodes.get(email).equals(verificationCode)) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPassword(newPassword);
                userRepository.save(user);

                // Remove the verification code after successful reset
                verificationCodes.remove(email);
                return true;
            }
        }
        return false;
    }

    // Helper method to send an email
    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // Generates a random 6-digit verification code
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates a 6-digit number
        return String.valueOf(code);
    }
}


