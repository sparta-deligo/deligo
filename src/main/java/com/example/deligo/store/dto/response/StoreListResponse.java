package com.example.deligo.store.dto.response;

import com.example.deligo.menu.dto.response.MenuResponse;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.entity.StoreCategory;
import com.example.deligo.store.entity.StoreStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class StoreListResponse {

    private Long id;
    private String name;
    private String category;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int minOrderAmount;
    private double averageRating;

    @Builder
    private StoreListResponse(
            Long id,
            String name,
            String category,
            LocalTime openTime,
            LocalTime closeTime,
            int minOrderAmount,
            double averageRating
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.averageRating = averageRating;
    }

    public static StoreListResponse from(Store store) {
        return StoreListResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .category(store.getCategory().getDescription())
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .averageRating(store.getAverageRating())
                .build();
    }
}