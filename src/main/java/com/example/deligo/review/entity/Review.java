package com.example.deligo.review.entity;

import com.example.deligo.common.entity.BaseDeletableEntity;
import com.example.deligo.order.entity.Order;
import com.example.deligo.store.entity.Store;
import com.example.deligo.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public Review(User user, Order order, int rating, String content, Store store) {
        this.user = user;
        this.order = order;
        this.store = order.getStore();
        this.rating = rating;
        this.content = content.trim();
    }

    public void updateReview(User user, int rating, String content) {
        if (!this.user.equals(user)) {
            throw new IllegalArgumentException("리뷰 수정 권한이 없습니다.");
        }
        this.rating = rating;
        this.content = content.trim();
    }

    public void deleteReview(User user) {
        if (!this.user.equals(user)) {
            throw new IllegalArgumentException("리뷰 삭제 권한이 없습니다.");
        }
        this.softDelete();
    }
}