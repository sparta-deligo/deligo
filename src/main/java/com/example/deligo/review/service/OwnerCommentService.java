package com.example.deligo.review.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.OwnerCommentRepository;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerCommentService {

    private final OwnerCommentRepository ownerCommentRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public OwnerComment createOwnerComment(User owner, Long reviewId, String content) {
        Review review = getReviewById(reviewId);

        if (!owner.isStoreOwner()) { // User 엔티티에 메서드 구현 필요
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        if (ownerCommentRepository.existsByReviewId(reviewId)) {
            throw new CustomException(ExceptionType.DUPLICATE_OWNER_COMMENT);
        }

        OwnerComment ownerComment = OwnerComment.builder()
                .owner(owner)
                .review(review)
                .content(content)
                .build();

        return ownerCommentRepository.save(ownerComment);
    }

    @Transactional
    public void updateOwnerComment(User owner, Long commentId, String newContent) {
        OwnerComment ownerComment = getOwnerCommentById(commentId);

        validateOwnerPermission(ownerComment, owner);

        ownerComment.updateContent(newContent);
    }

    @Transactional
    public void deleteOwnerComment(User owner, Long commentId) {
        OwnerComment ownerComment = getOwnerCommentById(commentId);

        validateOwnerPermission(ownerComment, owner);

        ownerCommentRepository.delete(ownerComment);
    }

    private Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionType.REVIEW_NOT_FOUND));
    }

    private OwnerComment getOwnerCommentById(Long commentId) {
        return ownerCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionType.OWNER_COMMENT_NOT_FOUND));
    }

    private void validateOwnerPermission(OwnerComment ownerComment, User owner) {
        if (!ownerComment.getOwner().equals(owner)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }
    }
}