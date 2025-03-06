package com.example.deligo.review.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.OwnerCommentRepository;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerCommentService {

    private final OwnerCommentRepository ownerCommentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createOwnerComment(Long userId, Long reviewId, String content) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionType.REVIEW_NOT_FOUND));

        if (!owner.isOwner()) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        if (!review.getStore().getOwnerId().equals(owner.getId())) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION, "다른 가게의 리뷰에 댓글을 작성할 수 없습니다.");
        }

        if (ownerCommentRepository.existsByReviewId(reviewId)) {
            throw new CustomException(ExceptionType.REVIEW_ALREADY_HAS_COMMENT);
        }

        OwnerComment ownerComment = OwnerComment.builder()
                .owner(owner)
                .review(review)
                .content(content)
                .build();

        ownerCommentRepository.save(ownerComment);
    }

    @Transactional
    public void updateOwnerComment(Long userId, Long commentId, String newContent) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        OwnerComment ownerComment = ownerCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionType.OWNER_COMMENT_NOT_FOUND));

        validateOwnerPermission(ownerComment, owner);
        ownerComment.updateContent(newContent);
    }

    @Transactional
    public void deleteOwnerComment(Long userId, Long commentId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        OwnerComment ownerComment = ownerCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionType.OWNER_COMMENT_NOT_FOUND));

        validateOwnerPermission(ownerComment, owner);
        ownerCommentRepository.delete(ownerComment);
    }

    private void validateOwnerPermission(OwnerComment ownerComment, User owner) {
        if (!ownerComment.getOwner().equals(owner)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }
    }
}