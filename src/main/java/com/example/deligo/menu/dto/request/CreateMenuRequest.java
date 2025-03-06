package com.example.deligo.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CreateMenuRequest {

    @NotNull
    private Long storeId;
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String description;

}
