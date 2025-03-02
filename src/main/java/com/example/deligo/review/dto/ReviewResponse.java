package com.example.deligo.review.dto;

import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;

public class ReviewResponse {
    private final Long id;
    private final Long userId;
    private final Long orderId;
    private final int rating;
    private final String content;
    private final String ownerComment;

    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.userId = review.getUser().getId();
        this.orderId = review.getOrder().getId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.ownerComment = review.getOwnerComment()
                .map(OwnerComment::getContent)
                .orElse(null);
    }
}
