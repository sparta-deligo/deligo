package com.example.deligo.store.entity;

public enum StoreCategory {

    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    CHICKEN("치킨"),
    PIZZA("피자"),
    FAST_FOOD("패스트푸드"),
    STEW("찜,탕"),
    SNACK("분식"),
    CAFE_DESSERT("카페,디저트");

    private final String description;

    StoreCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
