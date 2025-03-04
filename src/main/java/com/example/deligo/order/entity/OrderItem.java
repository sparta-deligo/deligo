package com.example.deligo.order.entity;

import com.example.deligo.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Setter
    private Order order;

    @Column(nullable = false)
    private BigDecimal price;  //변경 가능성이 있으므로 따로 필드에 저장

    @Column(nullable = false)
    private int quantity;

    public OrderItem(Menu menu, Order order, BigDecimal price, int quantity) {
        this.menu = menu;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
    }
}
