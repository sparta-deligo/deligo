package com.example.deligo.order.dto.request;

import com.example.deligo.order.entity.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEditRequest {
    private OrderStatus orderStatus;

    public OrderEditRequest(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
