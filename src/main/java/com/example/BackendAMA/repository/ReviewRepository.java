
package com.example.BackendAMA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.BackendAMA.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
}
