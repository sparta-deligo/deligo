package com.example.deligo.review.entity;

import com.example.deligo.common.entity.BaseDeletableEntity;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id",nullable = false, unique = true)
    private Review review;

    private String content;

    @Builder
    public OwnerComment(User owner, Review review, String content) {
        this.owner = owner;
        this.review = review;
        this.content = content;
    }
}
