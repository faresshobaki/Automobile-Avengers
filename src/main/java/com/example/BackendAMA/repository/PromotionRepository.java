package com.example.BackendAMA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.BackendAMA.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    @Query("SELECT SUM(p.claimedCount) FROM Promotion p")
    long sumClaimedCount();
}