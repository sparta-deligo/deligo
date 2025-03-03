package com.example.deligo.store.dto;

import com.example.deligo.store.entity.Store;
import lombok.Getter;

import java.util.UUID;

@Getter
public class StoreResponse {
    private Long id;
    private String name;
    private final double averageRating;

    public StoreResponse(Long id, String name, double averageRating) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
    }

    public static StoreResponse from(Store store, double averageRating) {
        return new StoreResponse(store.getId(), store.getName(), averageRating);
    }
}
