package com.example.deligo.store.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreSaveRequestDto {

    @NotBlank(message = "가게 이름은 필수입니다")
    private String name;

    @NotNull(message = "오픈 시간은 필수 입니다")
    private LocalTime openTime;

    @NotNull(message = "닫는 시간은 필수입니다")
    private LocalTime closeTime;

    @Min(value = 0, message = "최소 주문 금액은 0 이상이어야 합니다")
    private int minOrderAmount;

    @NotBlank(message = "카테고리는 필수입니다")
    private String category;

    /*@NotNull(message = "사장님 id는 필수입니다")
    private Long ownerId;*/

    private Double averageRating;
}
