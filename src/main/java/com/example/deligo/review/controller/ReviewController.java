package com.example.deligo.review.controller;

import com.example.deligo.review.entity.Review;
import com.example.deligo.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{orderId}")
    public ResponserEntity<Review> createReview(@PathVariable Long orderId, @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.createReview(orderId,request));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<Review>> getReviews(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int minRating,
            @RequestParam(defaultValue = "5") int maxRating) {
        return ResponserEntity.ok(reviewService.getReviewsByStore(storeId,minRating,maxRating));
    }
}
