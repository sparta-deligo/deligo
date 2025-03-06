package com.example.deligo.store.dto.Response;

import com.example.deligo.menu.dto.response.MenuResponse;
import com.example.deligo.store.entity.StoreCategory;
import com.example.deligo.store.entity.StoreStatus;
import lombok.Getter;
import org.hibernate.engine.spi.Status;

import java.time.LocalTime;
import java.util.List;

@Getter
public class StoreResponseDto {
    private Long id;
    private Long ownerId;
    private String name;
    private StoreCategory category;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int minOrderAmount;
    private StoreStatus status;
    private double averageRating;
    private List<MenuResponse> menuList;

    public StoreResponseDto(Long id, Long ownerId, String name, StoreCategory category,
                            LocalTime openTime, LocalTime closeTime, int minOrderAmount,
                            StoreStatus status, double averageRating, List<MenuResponse> menuList) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.status = status;
        this.averageRating = averageRating;
        this.menuList = menuList;
    }
}
