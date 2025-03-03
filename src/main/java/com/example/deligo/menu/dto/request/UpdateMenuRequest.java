package com.example.deligo.menu.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class UpdateMenuRequest {

    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private String description;
    @NotNull
    private String status;
}
