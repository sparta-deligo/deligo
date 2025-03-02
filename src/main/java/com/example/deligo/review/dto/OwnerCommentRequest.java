package com.example.deligo.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OwnerCommentRequest {
    @NotNull(message = "리뷰 ID는 필수입니다.")
    private Long reviewId;

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    public OwnerCommentRequest(Long reviewId, String content) {
        this.reviewId = reviewId;
        this.content = content;
    }
}
