package com.example.deligo.store.dto;

import lombok.Getter;

@Getter
public class StoreResponse {
    private final double averageRating;

    public StoreResponse(double averageRating) {
        this.averageRating = averageRating;
    }

    public static StoreResponse from(double averageRating) {
        return new StoreResponse(averageRating);
    }
}
