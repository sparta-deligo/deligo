package com.example.deligo.menu.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.menu.dto.request.CreateMenuRequest;
import com.example.deligo.menu.dto.request.UpdateMenuRequest;
import com.example.deligo.menu.dto.response.MenuResponse;
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.entity.MenuStatus;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.order.repository.OrderRepository;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.entity.UserRole;
import com.example.deligo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public MenuResponse create(Long userId, CreateMenuRequest request) {
        User owner = getOwner(userId);
        Store store = getStore(request.getStoreId());
        if(!store.getOwner().equals(owner)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        Menu menu = menuRepository.save(
                Menu.builder()
                        .store(store)
                        .name(request.getName())
                        .description(request.getDescription())
                        .price(request.getPrice())
                        .status(MenuStatus.AVAILABLE)
                        .build()
        );

        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse update(Long userId, Long menuId, UpdateMenuRequest request) {
        User owner = getOwner(userId);
        Menu menu = getMenu(menuId);
        if(!menu.getStore().getOwner().equals(owner)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        menu.update(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                MenuStatus.valueOf(request.getStatus())
        );

        return MenuResponse.from(menu);
    }

    @Transactional
    public void delete(Long userId, Long menuId) {
        User owner = getOwner(userId);
        Menu menu = getMenu(menuId);
        if(!menu.getStore().getOwner().equals(owner)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        validateNoActiveOrders(menu);

        menu.updateStatus(MenuStatus.DELETED);
        menu.softDelete();
    }

    private User getOwner(Long userId) {
        User user = getUser(userId);
        if(!user.getRole().equals(UserRole.OWNER)) {
            throw new CustomException(ExceptionType.NO_PERMISSION_ACTION);
        }

        return user;
    }

    private Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ExceptionType.MENU_NOT_FOUND));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
    }

    private Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ExceptionType.STORE_NOT_FOUND));
    }

    private void validateNoActiveOrders(Menu menu) {
        if(orderRepository.existsActiveOrderByMenuId(menu.getId())) {
            throw new CustomException(ExceptionType.ACTIVE_ORDER_EXISTS);
        }
    }
}
