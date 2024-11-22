package com.example.BackendAMA.repository;

import com.example.BackendAMA.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Find user by email and password for login
    Optional<User> findByEmailAndPassword(String email, String password);
}
