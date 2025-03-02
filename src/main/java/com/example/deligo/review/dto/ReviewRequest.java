package com.example.deligo.review.dto;

import jakarta.validation.constraints.NotBlank;

public class ReviewRequest {
    @NotNull(message = "주문 ID는 필수입니다.")
    private Long orderId;

    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private int rating;

    @NotBlank(message = "리뷰 내용은 필수입니다.")
    private String content;

    public ReviewRequest(Long orderId, int )
}
