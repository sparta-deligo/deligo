package com.example.deligo.review.controller;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.common.jwt.UserId;
import com.example.deligo.review.dto.request.OwnerCommentRequest;
import com.example.deligo.review.service.OwnerCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class OwnerCommentController {

    private final OwnerCommentService ownerCommentService;

    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<Void> createOwnerComment(
            @UserId Long userId,
            @PathVariable Long reviewId,
            @Valid @RequestBody OwnerCommentRequest request
    ) {
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "댓글 내용은 필수입니다.");
        }

        ownerCommentService.createOwnerComment(userId, reviewId, request.getContent());
        return ResponseEntity.created(URI.create("/reviews/" + reviewId + "/comments")).build();
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> updateOwnerComment(
            @UserId Long userId,
            @PathVariable Long commentId,
            @Valid @RequestBody OwnerCommentRequest request
    ) {
        ownerCommentService.updateOwnerComment(userId, commentId, request.getContent());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteOwnerComment(
            @UserId Long userId,
            @PathVariable Long commentId
    ) {
        ownerCommentService.deleteOwnerComment(userId, commentId);
        return ResponseEntity.noContent().build();
    }
}