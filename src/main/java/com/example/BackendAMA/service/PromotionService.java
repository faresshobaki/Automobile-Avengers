package com.example.BackendAMA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackendAMA.model.Promotion;
import com.example.BackendAMA.repository.PromotionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Promotion addPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public boolean deletePromotion(Long id) {
        Optional<Promotion> promotion = promotionRepository.findById(id);
        if (promotion.isPresent()) {
            promotionRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Optional<Promotion> getPromotionById(Long id) {
        return promotionRepository.findById(id);
    }
    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public long getClaimedPromotionsCount() {
        return promotionRepository.sumClaimedCount();
    }
}