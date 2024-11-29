package com.example.BackendAMA.controller;

import com.example.BackendAMA.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<String> sendContactMessage(@RequestBody Map<String, String> request) {
        String firstName = request.get("first_name");
        String lastName = request.get("last_name");
        String customerEmail = request.get("email");
        String message = request.get("message");

        if (firstName == null || lastName == null || customerEmail == null || message == null) {
            return ResponseEntity.badRequest().body("All fields are required.");
        }

        String fullMessage = "Name: " + firstName + " " + lastName + "\n"
                + "Email: " + customerEmail + "\n"
                + "Message: " + message;

        // Send the email to the admin's email, but also include the sender's email in the body
        emailService.sendEmail("oark296@gmail.com", "Contact Us Form Submission", fullMessage);

        return ResponseEntity.ok("Message sent successfully!");
    }
}
