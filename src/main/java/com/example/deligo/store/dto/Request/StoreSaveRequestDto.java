package com.example.deligo.store.dto.Request;

import com.example.deligo.store.entity.StoreCategory;
import com.example.deligo.store.entity.StoreStatus;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreSaveRequestDto {

    private Long ownerId;
    private String name;
    private StoreCategory storeCategory;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int minOrderAmount;
    private StoreStatus status;
    private Double averageRating;
}
