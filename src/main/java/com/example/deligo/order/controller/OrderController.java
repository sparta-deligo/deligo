package com.example.deligo.order.controller;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.order.dto.request.SaveRequest;
import com.example.deligo.order.dto.response.SaveResponse;
import com.example.deligo.order.service.orderServ.OrderService;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.deligo.common.exception.ExceptionType.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderServ;

    /* 타 서비스 로직(서비스로 바꿔야함) */
    private final UserRepository userServ;
    private final StoreRepository storeServ;
    private final MenuRepository menuServ;

    @PostMapping("/{userId}")
    public ResponseEntity<SaveResponse> saveOrder(@PathVariable Long userId,
                                                  @Valid @RequestBody SaveRequest saveRequest) {
        User user = userServ.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Store store = storeServ.findById(saveRequest.getStoreId()).orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
        List<Menu> menus = saveRequest.getMenuIds().stream()
                .map(id -> menuServ.findById(id).orElseThrow(() -> new CustomException(MENU_NOT_FOUND)))
                .toList();

        SaveResponse saveResponse = orderServ.saveOrder(user, store, menus, saveRequest);

        return ResponseEntity.ok().body(saveResponse);
    }

    @GetMapping("/{order_id}")
    public void checkOrder(@PathVariable String order_id) {

    }
}
