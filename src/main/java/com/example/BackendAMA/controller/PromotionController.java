package com.example.BackendAMA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.BackendAMA.model.Promotion;
import com.example.BackendAMA.service.PromotionService;

import java.util.List;

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
}
