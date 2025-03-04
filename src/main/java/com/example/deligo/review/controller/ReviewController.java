package com.example.deligo.review.controller;

import com.example.deligo.common.dto.PaginationResponse;
import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.common.jwt.JwtUtil;
import com.example.deligo.review.dto.ReviewRequest;
import com.example.deligo.review.dto.ReviewResponse;
import com.example.deligo.review.service.ReviewService;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private User getAuthenticatedUser(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new CustomException(ExceptionType.UNAUTHORIZED);
        }

        String jwt = token.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(jwt);

        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody ReviewRequest request
    ) {
        User user = getAuthenticatedUser(token);
        ReviewResponse response = reviewService.createReview(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @RequestHeader("Authorization") String token,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequest request
    ) {
        User user = getAuthenticatedUser(token);
        return ResponseEntity.ok(reviewService.updateReview(user, reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @RequestHeader("Authorization") String token,
            @PathVariable Long reviewId
    ) {
        User user = getAuthenticatedUser(token);
        reviewService.deleteReview(user, reviewId);
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