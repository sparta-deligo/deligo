package com.example.deligo.order.entity;

import com.example.deligo.common.entity.BaseTimeEntity;
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.review.entity.Review;
import com.example.deligo.store.entity.Store;
import com.example.deligo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review; // 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private String deliverAddress;

    private String storeComment;

    private String riderComment;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime canceledAt = null;

    @Builder
    public Order(
            User user, Store store, Menu menu, String deliverAddress, String storeComment, String riderComment, OrderStatus status) {
        this.user = user;
        this.store = store;
        this.menu = menu;
        this.deliverAddress = deliverAddress;
        this.storeComment = storeComment;
        this.riderComment = riderComment;
        this.status = status;
    }
    public boolean hasReview() { // 추가
        return this.review != null;
    }
    public boolean isCompleted() { // 추가
        return this.status == OrderStatus.COMPLETED;
    }
    public void setReview(Review review) {
        this.review = review;
    }
}
