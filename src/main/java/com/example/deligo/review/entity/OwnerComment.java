package com.example.deligo.review.entity;

import com.example.deligo.common.entity.BaseDeletableEntity;
import com.example.deligo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "owner_comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerComment extends BaseDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String content;

    @Builder
    public OwnerComment(User owner, Review review, String content) {
        this.owner = owner;
        this.review = review;
        this.content = content;
    }
}
