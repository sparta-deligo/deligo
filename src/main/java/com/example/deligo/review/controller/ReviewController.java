package com.example.deligo.review.controller;

import com.example.deligo.review.dto.ReviewRequest;
import com.example.deligo.review.dto.ReviewResponse;
import com.example.deligo.review.service.ReviewService;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ReviewRequest request
    ) {
        User user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(reviewService.createReview(user, request));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @AuthenticationPrincipal String email,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request
    ) {
        User user = userService.findByEmail(email);
        return ResponseEntity.ok(reviewService.updateReview(user, reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal String email,
            @PathVariable Long reviewId
    ) {
        User user = userService.findByEmail(email);
        reviewService.deleteReview(user, reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/store/{storeId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(reviewService.getReviewsByStore(storeId));
    }
}