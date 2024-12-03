package com.example.BackendAMA.controller;

import com.example.BackendAMA.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        String token = adminService.authenticateAdmin(email, password);
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password."));
    }

    @PostMapping("/send-reset-code")
    public ResponseEntity<String> sendResetCode(@RequestBody Map<String, String> emailRequest) {
        String email = emailRequest.get("email");
        adminService.sendResetCode(email);
        return ResponseEntity.ok("Reset code sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> resetRequest) {
        String email = resetRequest.get("email");
        String resetCode = resetRequest.get("resetCode");
        String newPassword = resetRequest.get("newPassword");

        boolean success = adminService.resetPassword(email, resetCode, newPassword);
        if (success) {
            return ResponseEntity.ok("Password reset successful.");
        }
        return ResponseEntity.status(400).body("Invalid reset code or expired.");
    }
}
