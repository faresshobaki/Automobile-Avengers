package com.example.BackendAMA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.example.BackendAMA") 
public class BackendAMAApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(BackendAMAApplication.class, args);

        
        System.out.println("BackendAMA Application has started successfully!");
    }
}
