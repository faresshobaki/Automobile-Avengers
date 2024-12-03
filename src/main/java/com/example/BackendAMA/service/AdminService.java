package com.example.BackendAMA.service;

import com.example.BackendAMA.model.Admin;
import com.example.BackendAMA.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmailService emailService;

    public String authenticateAdmin(String email, String password) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return UUID.randomUUID().toString();
        }
        return null;
    }

    public void sendResetCode(String email) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            String resetCode = UUID.randomUUID().toString();
            admin.setResetCode(resetCode);
            adminRepository.save(admin);

            emailService.sendEmail(email, "Password Reset Code", "Your reset code is: " + resetCode);
        }
    }

    public boolean resetPassword(String email, String resetCode, String newPassword) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null && admin.getResetCode().equals(resetCode)) {
            admin.setPassword(newPassword);
            admin.setResetCode(null); 
            adminRepository.save(admin);
            return true;
        }
        return false;
    }
}
