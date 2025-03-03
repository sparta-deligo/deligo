package com.example.deligo.review.dto;

import com.example.deligo.common.entity.BaseTimeEntity;
import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponse extends BaseTimeEntity {
    private final Long id;
    private final Long userId;
    private final Long orderId;
    private final int rating;
    private final String content;
    private final LocalDateTime deletedAt;
    private final String ownerComment;

    private ReviewResponse(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.orderId = review.getOrder().getId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.deletedAt = review.getDeletedAt();
        this.ownerComment = review.getOwnerComment()
                .map(OwnerComment::getContent)
                .orElse(null);
    }

    public static ReviewResponse from(Review review) {
        return new ReviewResponse(review);
    }
}