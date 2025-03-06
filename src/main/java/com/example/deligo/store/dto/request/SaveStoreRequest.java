package com.example.deligo.store.dto.request;

import com.example.deligo.store.entity.StoreCategory;
import com.example.deligo.store.entity.StoreStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class SaveStoreRequest {

    @NotNull(message = "소유자 ID는 필수입니다.")
    private Long ownerId;

    @NotBlank(message = "가게 이름은 필수입니다.")
    @Size(min = 1, max = 50, message = "가게 이름은 2~50자 이내여야 합니다.")
    private String name;

    @NotNull(message = "카테고리는 필수입니다.")
    private StoreCategory storeCategory;

    @NotNull(message = "오픈 시간은 필수입니다")
    private LocalTime openTime;

    @NotNull(message = "마감 시간은 필수입니다")
    private LocalTime closeTime;

    @Min(value = 5000, message = "최소 주문 금액은 5000원 이상이어야 합니다.")
    private int minOrderAmount;

    @NotNull(message = "가게 상태는 필수입니다.")
    private StoreStatus status;

    @DecimalMin(value = "0.0", message = "평점은 0.0 이상이어야 합니다.")
    @DecimalMax(value = "5.0", message = "평점은 5.0 이하여야 합니다.")
    private Double averageRating;
}
