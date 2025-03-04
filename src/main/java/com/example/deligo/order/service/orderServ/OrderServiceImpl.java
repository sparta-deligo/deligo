package com.example.deligo.order.service.orderServ;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.order.dto.request.SaveRequest;
import com.example.deligo.order.dto.response.FindResponse;
import com.example.deligo.order.dto.response.OrderCancelResponse;
import com.example.deligo.order.dto.response.SaveResponse;
import com.example.deligo.order.dto.response.SetStatusResponse;
import com.example.deligo.order.entity.Order;
import com.example.deligo.order.entity.OrderItem;
import com.example.deligo.order.entity.OrderStatus;
import com.example.deligo.order.repository.OrderItemRepository;
import com.example.deligo.order.repository.OrderRepository;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.deligo.common.exception.ExceptionType.*;
import static com.example.deligo.order.entity.OrderStatus.CANCELED;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    /* 타 서비스 로직(서비스로 바꿔야함) */
    private final UserRepository userServ;
    private final StoreRepository storeServ;
    private final MenuRepository menuServ;

    @Override
    public SaveResponse saveOrder(User user, Store store, List<Menu> menus, SaveRequest saveRequest) {
        List<Long> menuIds = saveRequest.getMenuIds();

        Order order = new Order(user, store, new ArrayList<>(), saveRequest);
        orderRepo.save(order);

        List<OrderItem> orderItems = menus.stream()
                .map(menu -> {
                    OrderItem orderItem = new OrderItem(menu, order, menu.getPrice(), saveRequest.getQuantity());
                    order.addOrderItems(orderItem);
                    return orderItem;
                })
                .toList();
        orderItemRepo.saveAll(orderItems);

        return new SaveResponse(order);
    }

    @Override
    public FindResponse findById(Long id) {
        Order order = findOrder(id);

        return new FindResponse(order);
    }

    @Override
    public OrderCancelResponse cancelOrder(Long loginUserId, Long orderId) {
        Order order = findOrder(orderId);
        if (!loginUserId.equals(order.getUser().getId())) {
            throw new CustomException(NO_PERMISSION_ACTION);
        }

        OrderStatus status = order.getStatus();
        switch (status) {
            case ORDER_RECEIVED:
                order.cancelOrder();
                return new OrderCancelResponse(order);
            case CANCELED:
                throw new CustomException(ALREADY_CANCELED);
            default:
                throw new CustomException(ALREADY_ORDERED);
        }
    }

    @Override
    public SetStatusResponse setOrderStatus(Long loginUserId, Long orderId, OrderStatus status) {
        Order order = findOrder(orderId);

        if (order.getStore().getOwner().getId().equals(loginUserId)) {
            isCanceled(order);
            order.setOrderStatus(status);
            return new SetStatusResponse(order);
        } else {
            throw new CustomException(NO_PERMISSION_ACTION);
        }
    }

    private static void isCanceled(Order order) {
        if (order.getStatus().equals(CANCELED)) {
            throw new CustomException(ALREADY_CANCELED);
        }
    }

    private Order findOrder(Long id) {
        return orderRepo.findById(id).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));
    }
}