package com.example.deligo.order.service.orderServ;

import com.example.deligo.menu.entity.Menu;
import com.example.deligo.order.dto.request.SaveRequest;
import com.example.deligo.order.dto.response.FindResponse;
import com.example.deligo.order.dto.response.OrderCancelResponse;
import com.example.deligo.order.dto.response.SaveResponse;
import com.example.deligo.order.dto.response.SetStatusResponse;
import com.example.deligo.order.entity.OrderStatus;
import com.example.deligo.store.entity.Store;
import com.example.deligo.user.entity.User;

import java.util.List;

public interface OrderService {
    SaveResponse saveOrder(User user, Store store, List<Menu> menus, SaveRequest saveRequest);

    FindResponse findById(Long id);

    OrderCancelResponse cancelOrder(Long loginUserId, Long id);

    SetStatusResponse setOrderStatus(Long loginUserId, Long orderId, OrderStatus status);
}
