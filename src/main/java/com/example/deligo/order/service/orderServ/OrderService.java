package com.example.deligo.order.service.orderServ;

import com.example.deligo.order.dto.request.SaveRequest;
import com.example.deligo.order.dto.response.*;
import com.example.deligo.order.entity.OrderStatus;

public interface OrderService {
    SaveResponse saveOrder(Long userId, SaveRequest saveRequest);

    FindResponse findUserOrder(Long userId, Long orderId);

    FindStoreOrderResponse findStoreOrder(Long loginUserId, Long orderId);

    OrderCancelResponse cancelOrder(Long loginUserId, Long id);

    SetStatusResponse setOrderStatus(Long loginUserId, Long orderId, OrderStatus status);
}
