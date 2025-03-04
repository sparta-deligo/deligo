package com.example.deligo.review.entity;

import com.example.deligo.common.entity.BaseDeletableEntity;
import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "owner_comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerComment extends BaseDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false, unique = true)
    private Review review;

    @Column(nullable = false, length = 500)
    private String content;

    @Builder
    public OwnerComment(User owner, Review review, String content) {
        validate(owner, review, content);
        this.owner = owner;
        this.review = review;
        this.content = content.trim();
    }

    private void validate(User owner, Review review, String content) {
        if (owner == null) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "댓글 작성자는 필수입니다.");
        }
        if (review == null) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "리뷰 정보는 필수입니다.");
        }
        if (content == null || content.isBlank()) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "댓글 내용은 비어 있을 수 없습니다.");
        }
    }

    public void updateContent(String newContent) {
        if (newContent == null || newContent.isBlank()) {
            throw new CustomException(ExceptionType.INVALID_REQUEST, "댓글 내용은 비어 있을 수 없습니다.");
        }
        this.content = newContent.trim();
    }
}