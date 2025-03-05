package com.example.deligo.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class OwnerCommentRequest {
    @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
    @Size(min = 1, max = 1000, message = "댓글은 최소 1자 이상, 최대 1000자 이하로 입력해야 합니다.")
    private String content;
}