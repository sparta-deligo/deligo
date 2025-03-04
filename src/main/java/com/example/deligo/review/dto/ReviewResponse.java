package com.example.deligo.review.dto;

import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {
    private Long id;
    private Long userId;
    private Long orderId;
    private int rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String ownerComment;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.orderId = review.getOrder().getId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
        this.ownerComment = extractOwnerComment(review);
    }

    private String extractOwnerComment(Review review) {
        return review.getOwnerComment()
                .map(OwnerComment::getContent)
                .orElse(null);
    }

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(review);
    }
}