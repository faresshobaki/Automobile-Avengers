package com.example.BackendAMA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BackendAMA.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
