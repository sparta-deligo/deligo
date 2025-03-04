package com.example.deligo.review.controller;

import com.example.deligo.common.jwt.JwtUtil;
import com.example.deligo.review.dto.request.OwnerCommentRequest;
import com.example.deligo.review.dto.response.OwnerCommentResponse;
import com.example.deligo.review.service.OwnerCommentService;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/reviews/{reviewId}/comments")
@RequiredArgsConstructor
public class OwnerCommentController {

    private final OwnerCommentService ownerCommentService;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    private User getAuthenticatedUser(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Unauthorized");
        }
        String jwt = token.substring(7);
        return userService.findById(jwtUtil.getUserIdFromToken(jwt));
    }

    @PostMapping
    public ResponseEntity<OwnerCommentResponse> createOwnerComment(
            @RequestHeader("Authorization") String token,
            @PathVariable Long reviewId,
            @Valid @RequestBody OwnerCommentRequest request
    ) {
        User owner = getAuthenticatedUser(token);
        OwnerCommentResponse response = ownerCommentService.createOwnerComment(owner, reviewId, request);
        return ResponseEntity.created(URI.create("/reviews/" + reviewId + "/comments/" + response.getId())).body(response);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<OwnerCommentResponse> updateOwnerComment(
            @RequestHeader("Authorization") String token,
            @PathVariable Long reviewId,
            @PathVariable Long commentId,
            @Valid @RequestBody OwnerCommentRequest request
    ) {
        User owner = getAuthenticatedUser(token);
        return ResponseEntity.ok(ownerCommentService.updateOwnerComment(owner, reviewId, commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteOwnerComment(
            @RequestHeader("Authorization") String token,
            @PathVariable Long reviewId,
            @PathVariable Long commentId
    ) {
        User owner = getAuthenticatedUser(token);
        ownerCommentService.deleteOwnerComment(owner, reviewId, commentId);
        return ResponseEntity.noContent().build();
    }
}