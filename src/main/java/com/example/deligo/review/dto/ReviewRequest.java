package com.example.deligo.review.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewRequest {

    @NotNull(message = "주문 ID는 필수입니다.")
    private Long orderId;

    @Min(value = 1, message = "별점은 최소 1점 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점까지 입력할 수 있습니다.")
    private int rating;

    @NotBlank(message = "리뷰 내용은 비어있을 수 없습니다.")
    @Size(min = 1, max = 1000, message = "리뷰는 최소 1자 이상, 최대 1000자 이하로 입력해야 합니다.")
    private String content;
}