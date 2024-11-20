package com.example.BackendAMA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.example.BackendAMA") // Ensures scanning of all packages under com.example.BackendAMA
public class BackendAMAApplication {

    public static void main(String[] args) {
        // Start the Spring Boot application
        SpringApplication.run(BackendAMAApplication.class, args);

        // Log a success message when the application starts successfully
        System.out.println("BackendAMA Application has started successfully!");
    }
}
