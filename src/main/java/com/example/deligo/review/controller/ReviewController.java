package com.example.deligo.review.controller;

import com.example.deligo.common.dto.PaginationResponse;
import com.example.deligo.common.jwt.UserId;
import com.example.deligo.review.dto.request.ReviewRequest;
import com.example.deligo.review.dto.response.ReviewResponse;
import com.example.deligo.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @UserId Long userId,
            @Valid @RequestBody ReviewRequest request
    ) {
        ReviewResponse response = reviewService.createReview(userId, request);
        return ResponseEntity.created(URI.create("/reviews/" + response.getId())).body(response);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @UserId Long userId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request
    ) {
        return ResponseEntity.ok(reviewService.updateReview(userId, reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @UserId Long userId,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(userId, reviewId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stores/{storeId}/reviews")
    public ResponseEntity<PaginationResponse<ReviewResponse>> getReviewsByStore(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ReviewResponse> reviewPage = reviewService.getReviewsByStore(storeId, page, size);
        return ResponseEntity.ok(new PaginationResponse<>(reviewPage));
    }
}