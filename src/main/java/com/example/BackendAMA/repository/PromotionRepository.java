package com.example.BackendAMA.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BackendAMA.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
	Optional<Promotion> findById(Long id);
    long countByClaimed(boolean claimed);
}
