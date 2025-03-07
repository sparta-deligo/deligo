package com.example.deligo.store.dto.request;

import com.example.deligo.store.entity.StoreCategory;
import com.example.deligo.store.entity.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class UpdateStoreRequest {

    private String name;
    private StoreCategory storeCategory;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int minOrderAmount;
    private StoreStatus status;

    public UpdateStoreRequest(
            String name,
            StoreCategory storeCategory,
            LocalTime openTime,
            LocalTime closeTime,
            int minOrderAmount,
            StoreStatus status
    ) {
        this.name = name;
        this.storeCategory = storeCategory;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minOrderAmount = minOrderAmount;
        this.status = status;
    }
}
