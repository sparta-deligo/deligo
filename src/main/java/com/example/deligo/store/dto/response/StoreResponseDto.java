package com.example.deligo.store.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class StoreResponseDto {

    private final Long id;
    private final String name;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minOrderAmount;
    private final String category;
    private final double averageRating;

    public StoreResponseDto(Long id, String name, LocalTime openTime, LocalTime closeTime, int minOrderAmount, String category,  double averageRating) {
        this.id = id;
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.averageRating = averageRating;
    }
}
