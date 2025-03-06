package com.example.deligo.menu.dto.request;

import com.example.deligo.menu.entity.MenuStatus;
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
    private MenuStatus status;
}
