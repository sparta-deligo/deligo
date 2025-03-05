package com.example.deligo.store.dto.Request;

import com.example.deligo.store.entity.StoreCategory;
import com.example.deligo.store.entity.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreUpdateRequestDto {

    private final Long ownerId;
    private final String name;
    private final StoreCategory storeCategory;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final int minOrderAmount;
    private final StoreStatus status;

    public StoreUpdateRequestDto(Long ownerId, String name, StoreCategory storeCategory, LocalTime openTime, LocalTime closeTime, int minOrderAmount, StoreStatus status) {
        this.ownerId = ownerId;
        this.name = name;
        this.storeCategory = storeCategory;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.status = status;
    }
}
