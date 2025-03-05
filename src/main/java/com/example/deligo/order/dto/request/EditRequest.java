package com.example.deligo.order.dto.request;

import com.example.deligo.order.entity.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditRequest {
    private OrderStatus orderStatus;

    public EditRequest(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
