package com.example.BackendAMA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BackendAMA.model.Review;
import com.example.BackendAMA.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }
}
