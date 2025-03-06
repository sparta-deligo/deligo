package com.example.deligo.order.service.orderServ;

import com.example.deligo.order.dto.request.OrderSaveRequest;
import com.example.deligo.order.dto.response.*;
import com.example.deligo.order.entity.OrderStatus;

public interface OrderService {
    OrderSaveResponse saveOrder(Long userId, OrderSaveRequest orderSaveRequest);

    OrderFindResponse findUserOrder(Long userId, Long orderId);

    FindStoreOrderResponse findStoreOrder(Long loginUserId, Long orderId);

    OrderCancelResponse cancelOrder(Long loginUserId, Long id);

    SetOrderStatusResponse setOrderStatus(Long loginUserId, Long orderId, OrderStatus status);
}
