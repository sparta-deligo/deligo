package com.example.deligo.review.dto.response;

import com.example.deligo.review.entity.OwnerComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OwnerCommentResponse {
    private Long id;
    private Long reviewId;
    private Long ownerId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OwnerCommentResponse(OwnerComment comment) {
        this.id = comment.getId();
        this.reviewId = comment.getReview().getId();
        this.ownerId = comment.getOwner().getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}