package com.example.deligo.review.controller;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.review.dto.request.OwnerCommentRequest;
import com.example.deligo.review.service.OwnerCommentService;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class OwnerCommentController {
//
//    private final OwnerCommentService ownerCommentService;
//    private final JwtUtil jwtUtil;
//    private final UserService userService;
//
//    private User getAuthenticatedUser(String token) {
//        if (token == null || !token.startsWith("Bearer ")) {
//            throw new CustomException(ExceptionType.UNAUTHORIZED);
//        }
//        String jwt = token.substring(7);
//        return userService.findById(jwtUtil.getUserIdFromToken(jwt))
//                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
//    }
//
//    @PostMapping("/{reviewId}/comments")
//    public ResponseEntity<Void> createOwnerComment(
//            @RequestHeader("Authorization") String token,
//            @PathVariable Long reviewId,
//            @Valid @RequestBody OwnerCommentRequest request
//    ) {
//        User owner = getAuthenticatedUser(token);
//
//        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
//            throw new CustomException(ExceptionType.INVALID_REQUEST, "댓글 내용은 필수입니다.");
//        }
//
//        ownerCommentService.createOwnerComment(owner, reviewId, request.getContent());
//        return ResponseEntity.created(URI.create("/reviews/" + reviewId + "/comments")).build();
//    }
//
//    @PutMapping("/comments/{commentId}")
//    public ResponseEntity<Void> updateOwnerComment(
//            @RequestHeader("Authorization") String token,
//            @PathVariable Long commentId,
//            @Valid @RequestBody OwnerCommentRequest request
//    ) {
//        User owner = getAuthenticatedUser(token);
//        ownerCommentService.updateOwnerComment(owner, commentId, request.getContent());
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/comments/{commentId}")
//    public ResponseEntity<Void> deleteOwnerComment(
//            @RequestHeader("Authorization") String token,
//            @PathVariable Long commentId
//    ) {
//        User owner = getAuthenticatedUser(token);
//        ownerCommentService.deleteOwnerComment(owner, commentId);
//        return ResponseEntity.noContent().build();
//    }
}