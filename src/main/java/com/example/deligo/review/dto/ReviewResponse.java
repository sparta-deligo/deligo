package com.example.deligo.review.dto;

import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse {
    private final Long id;
    private final Long userId;
    private final Long orderId;
    private final int rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String ownerComment;

    public ReviewResponse(Review review, String ownerComment) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.orderId = review.getOrder().getId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
        this.ownerComment = ownerComment;
    }

    public static ReviewResponse from(Review review) {
        String ownerComment = review.getOwnerComment()
                .map(OwnerComment::getContent)
                .orElse(null);
        return new ReviewResponse(review, ownerComment);
    }
}