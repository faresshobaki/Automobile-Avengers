package com.example.BackendAMA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BackendAMA.model.Promotion;
import com.example.BackendAMA.service.PromotionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

    @PostMapping
    public ResponseEntity<Promotion> addPromotion(@RequestBody Promotion promotion) {
        Promotion newPromotion = promotionService.addPromotion(promotion);
        return ResponseEntity.ok(newPromotion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePromotion(@PathVariable Long id) {
        boolean isDeleted = promotionService.deletePromotion(id);
        if (isDeleted) {
            return ResponseEntity.ok("Promotion deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Promotion not found.");
        }
    }
    @PostMapping("/{id}/claim")
    public ResponseEntity<String> claimPromotion(@PathVariable Long id) {
        Optional<Promotion> promotionOpt = promotionService.getPromotionById(id);
        if (promotionOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Promotion not found.");
        }

        Promotion promotion = promotionOpt.get();
        promotion.setClaimedCount(promotion.getClaimedCount() + 1); // Increment the claimed count
        promotionService.save(promotion); // Save the updated promotion

        return ResponseEntity.ok("Promotion claimed successfully.");
    }
    @GetMapping("/claimed-count")
    public ResponseEntity<Map<String, Long>> getClaimedPromotionsCount() {
        long count = promotionService.getClaimedPromotionsCount(); 
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
}
