package com.example.deligo.order.service.orderServ;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.order.dto.request.SaveRequest;
import com.example.deligo.order.dto.response.FindResponse;
import com.example.deligo.order.dto.response.SaveResponse;
import com.example.deligo.order.entity.Order;
import com.example.deligo.order.entity.OrderItem;
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

import static com.example.deligo.common.exception.ExceptionType.ORDER_NOT_FOUND;

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
        Order order = orderRepo.findById(id).orElseThrow(() -> new CustomException(ORDER_NOT_FOUND));

        return new FindResponse(order);
    }
}