package com.example.deligo.review.entity;

import com.example.deligo.common.entity.BaseDeletableEntity;
import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.order.entity.Order;
import com.example.deligo.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Entity
@Table(name = "reviews", uniqueConstraints = {@UniqueConstraint(columnNames = "order_id")})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private OwnerComment ownerComment;

    @Builder
    public Review(User user, Order order, int rating, String content) {
        validateReviewConditions(user, order, rating, content);
        this.user = user;
        this.order = order;
        this.rating = rating;
        this.content = content.trim();
    }

    private void validateReviewConditions(User user, Order order, int rating, String content) {
        if (!order.isCompleted()) {
            throw new CustomException(ExceptionType.REVIEW_CONDITION_NOT_MET);
        }
        if (order.getReview() != null) {
            throw new CustomException(ExceptionType.DUPLICATE_RESOURCE, "이미 작성된 리뷰가 있습니다.");
        }
        validateRating(rating);
        validateContent(content);
    }

    private void validateRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "별점은 1~5점 사이여야 합니다.");
        }
    }

    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "리뷰 내용은 비어 있을 수 없습니다.");
        }
    }

    public void updateReview(User user, int rating, String content) {
        if (!this.user.equals(user)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }
        validateRating(rating);
        validateContent(content);
        this.rating = rating;
        this.content = content.trim();
    }

    public void deleteReview(User user) {
        if (!this.user.equals(user)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }
        if (this.ownerComment != null) {
            removeOwnerComment();
        }
        this.softDelete();
    }

    public void addOwnerComment(User owner, String commentContent) {
        validateOwnerCommentConditions(owner);
        this.ownerComment = OwnerComment.builder()
                .review(this)
                .owner(owner)
                .content(commentContent.trim())
                .build();
    }

    private void validateOwnerCommentConditions(User owner) {
        if (!owner.isOwner()) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }
        if (this.ownerComment != null) {
            throw new CustomException(ExceptionType.DUPLICATE_RESOURCE, "이미 댓글이 작성되었습니다.");
        }
    }

    public void removeOwnerComment() {
        this.ownerComment = null;
    }

    public Optional<OwnerComment> getOwnerComment() {
        return Optional.ofNullable(this.ownerComment);
    }

    public boolean isOrderCompleted() {
        return this.order.isCompleted();
    }

    public boolean canWriteReview() {
        return this.order.isCompleted() && this.order.getReview() == null;
    }
}