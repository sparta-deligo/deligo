package com.example.deligo.store.dto.request;

import com.example.deligo.common.enums.StoreStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class StoreUpdateRequestDto {

    private final String name;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minOrderAmount;
    private final String category;
    private final StoreStatus status;

    public StoreUpdateRequestDto(String name, LocalTime openTime, LocalTime closeTime, int minOrderAmount, String category, StoreStatus status) {
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.status = status;
    }
}
