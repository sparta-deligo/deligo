package com.example.deligo.order.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class OrderSaveRequest {

    @NotNull
    private Long storeId;

    @NotNull
    private Map<Long, Integer> menus;

    @NotBlank
    private String deliverAddress;

    @NotNull
    @Size(max = 50, message = "50자까지만 입력 가능합니다.")
    private String storeComment;

    @NotNull
    @Size(max = 50, message = "50자까지만 입력 가능합니다.")
    private String riderComment;

    public OrderSaveRequest(String deliverAddress, Map<Long, Integer> menus, String riderComment, String storeComment, Long storeId) {
        this.deliverAddress = deliverAddress;
        this.menus = menus;
        this.riderComment = riderComment;
        this.storeComment = storeComment;
        this.storeId = storeId;
    }
}