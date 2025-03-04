package com.example.deligo.order.entity;

import com.example.deligo.common.entity.BaseTimeEntity;
<<<<<<< HEAD
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.review.entity.Review;
=======
import com.example.deligo.order.dto.request.SaveRequest;
>>>>>>> feat/order-#9-create-order
import com.example.deligo.store.entity.Store;
import com.example.deligo.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.deligo.order.entity.OrderStatus.ORDER_RECEIVED;

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

<<<<<<< HEAD
    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review; // 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
=======
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    private Menu menu = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();
>>>>>>> feat/order-#9-create-order

    private String deliverAddress;

    private String storeComment;

    private String riderComment;

    @Enumerated(EnumType.STRING)
<<<<<<< HEAD
    private OrderStatus status = ORDER_RECEIVED;
=======
    @Column(nullable = false)
    private OrderStatus status;
>>>>>>> feat/signup

    private LocalDateTime canceledAt = null;

    @Builder
    public Order(User user, Store store, List<OrderItem> orderItems, String deliverAddress, String storeComment, String riderComment) {
        this.user = user;
        this.store = store;
        this.orderItems = orderItems;
        this.deliverAddress = deliverAddress;
        this.storeComment = storeComment;
        this.riderComment = riderComment;
    }

    public Order(User user, Store store, List<OrderItem> orderItems, SaveRequest req) {
        this.user = user;
        this.store = store;
        this.orderItems = orderItems;
        this.deliverAddress = req.getDeliverAddress();
        this.storeComment = req.getStoreComment();
        this.riderComment = req.getRiderComment();
    }

    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
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
