package com.example.deligo.menu.entity;

import com.example.deligo.common.entity.BaseDeletableEntity;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.entity.StoreStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private String name;

    private String description;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private MenuStatus status;

    @Builder
    public Menu(Store store, String name, String description, BigDecimal price, MenuStatus status) {
        this.store = store;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }
}
