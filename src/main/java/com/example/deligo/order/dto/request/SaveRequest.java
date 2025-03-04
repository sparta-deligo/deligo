package com.example.deligo.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SaveRequest {

    @Positive
    @NotNull
    private Long storeId;

    @Positive
    @NotNull
    private List<Long> menuIds = new ArrayList<>();

    @NotNull
    private int quantity;

    @NotBlank
    private String deliverAddress;

    @NotNull
    private String storeComment;

    @NotNull
    private String riderComment;

    public SaveRequest(String deliverAddress, List<Long> menuIds, int quantity, String riderComment, String storeComment, Long storeId) {
        this.deliverAddress = deliverAddress;
        this.menuIds = menuIds;
        this.quantity = quantity;
        this.riderComment = riderComment;
        this.storeComment = storeComment;
        this.storeId = storeId;
    }
}
