package com.example.deligo.menu.dto.response;

import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.entity.MenuStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuResponse {

    private Long id;
    private Long storeId;
    private String name;
    private String description;
    private BigDecimal price;
    private MenuStatus status;

    @Builder
    private MenuResponse(Long id, Long storeId, String name, String description, BigDecimal price, MenuStatus status) {
        this.id = id;
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    public static MenuResponse from(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .storeId(menu.getStore().getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .price(menu.getPrice())
                .status(menu.getStatus())
                .build();
    }
}
