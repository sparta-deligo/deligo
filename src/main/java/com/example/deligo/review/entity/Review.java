package com.example.deligo.review.entity;

import com.example.deligo.common.entity.BaseDeletableEntity;
import com.example.deligo.order.entity.Order;
import com.example.deligo.store.entity.Store;
import com.example.deligo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private int rating;

    private String content;

    @Builder
    public Review(User user, Order order, int rating, String content) {
        this.user = user;
        this.order = order;
        this.rating = rating;
        this.content = content;
    }
}
