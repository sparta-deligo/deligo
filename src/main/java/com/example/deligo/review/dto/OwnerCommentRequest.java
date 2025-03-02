package com.example.deligo.review.dto;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class OwnerCommentRequest {
    @NotNull(message = "리뷰 ID는 필수입니다.")
    private Long reviewId;

    @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
    @Size(min = 1, max = 500, message = "댓글은 최소 1자 이상, 최대 500자 이하로 입력해야 합니다.")
    private String content;

    public static OwnerCommentRequest from(Long reviewId, String content) {
        if(reviewId == null) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "리뷰 ID는 필수입니다.");
        }
        if(content == null || content.isBlank()) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "댓글 내용은 비어있을 수 없습니다.");
        }
        return new OwnerCommentRequest(reviewId, content.trim());
    }
}