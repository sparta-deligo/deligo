package com.example.deligo.review.dto;

import com.example.deligo.common.entity.BaseTimeEntity;
import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;

import java.time.LocalDateTime;
import java.util.Optional;

public class ReviewResponse extends BaseTimeEntity {
    private Long id;
    private Long userId;
    private Long orderId;
    private int rating;
    private String content;
    private LocalDateTime deletedAt;
    private String ownerComment;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.orderId = review.getOrder().getId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.deletedAt = review.getDeletedAt();
        this.ownerComment = Optional.ofNullable(review.getOwnerComment())
                .map(OwnerComment::getContent)
                .orElse(null);
    }

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(review);
    }
}