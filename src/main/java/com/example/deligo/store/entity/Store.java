package com.example.deligo.store.entity;

import com.example.deligo.common.entity.BaseEntity;
import com.example.deligo.common.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "store")
@AllArgsConstructor
public class Store extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name; //가게 이름

    @Column(nullable = false)
    private LocalTime openTime; //가게 open 시간

    @Column(nullable = false)
    private LocalTime closeTime; //가게 close 시간

    @Column(nullable = false)
    private int minOrderAmount; //가게 최소 주문 금액

    @Column(nullable = false)
    private String category; //가게 카테고리

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStatus status; //가게 상태

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner; //사장 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu; //가게 메뉴

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 가게 주문*/

    @Column(nullable = false)
    private Double average_rating = 0.0; //별점 평균

    public Store(String name) {}

    public void update(String name, LocalTime openTime, LocalTime closeTime, int minOrderAmount, String category, StoreStatus status){
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.status = status;
    }
}
