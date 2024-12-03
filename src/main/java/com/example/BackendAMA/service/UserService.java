package com.example.BackendAMA.service;

import com.example.BackendAMA.model.User;
import com.example.BackendAMA.repository.UserRepository;
import com.example.BackendAMA.util.JwtUtil;

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

    
    public User verifyCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean sendVerificationCode(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String verificationCode = generateVerificationCode();
            verificationCodes.put(email, verificationCode);

            sendEmail(email, "Password Reset Verification Code", "Your verification code is: " + verificationCode);

            return true;
        }
        return false;
    }

    public boolean resetPassword(String email, String verificationCode, String newPassword) {
        if (verificationCodes.containsKey(email) && verificationCodes.get(email).equals(verificationCode)) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPassword(newPassword);
                userRepository.save(user);

                verificationCodes.remove(email);
                return true;
            }
        }
        return false;
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); 
        return String.valueOf(code);
    }

    public boolean validateSession(String token) {
        return JwtUtil.validateToken(token);
    }
}


