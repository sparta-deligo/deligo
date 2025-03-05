package com.example.deligo.order.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.order.dto.request.SaveRequest;
import com.example.deligo.order.dto.response.*;
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
import static com.example.deligo.user.entity.UserRole.OWNER;

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
    public FindResponse findUserOrder(Long userId, Long orderId) {
        Order order = findOrder(orderId);
        userOrderCheck(userId, order);

        return new FindResponse(order);
    }

    @Override
    public FindStoreOrderResponse findStoreOrder(Long loginUserId, Long orderId) {
        User owner = isOwner(loginUserId);
        Order order = findOrder(orderId);

        if (order.getStore().getOwner().getId().equals(owner.getId())) {
            return new FindStoreOrderResponse(order);
        } else {
            throw getNoPermissionException();
        }
    }

    @Override
    public OrderCancelResponse cancelOrder(Long loginUserId, Long orderId) {
        Order order = findOrder(orderId);
        userOrderCheck(loginUserId, order);

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
            throw getNoPermissionException();
        }
    }

    private static CustomException getNoPermissionException() {
        return new CustomException(NO_PERMISSION_ACTION);
    }

    /**
     * 취소된 주문인지 확인
     */
    private static void isCanceled(Order order) {
        if (order.getStatus().equals(CANCELED)) {
            throw new CustomException(ALREADY_CANCELED);
        }
    }

    /**
     * 내 주문 확인
     */
    private Order findOrder(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));
    }

    /**
     * 내 주문이 맞는지 확인
     */
    private static void userOrderCheck(Long loginUserId, Order order) {
        if (!order.getUser().getId().equals(loginUserId)) {
            throw getNoPermissionException();
        }
    }

    /**
     * 가게 주인이 맞는지 확인
     */
    private User isOwner(Long loginUserId) {
        if (!userServ.findById(loginUserId).get().getRole().equals(OWNER)) {
            throw getNoPermissionException();
        } else {
            return userServ.findById(loginUserId).get();
        }
    }
}