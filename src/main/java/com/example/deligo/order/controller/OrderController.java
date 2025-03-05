package com.example.deligo.order.controller;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.order.dto.request.EditRequest;
import com.example.deligo.order.dto.request.SaveRequest;
import com.example.deligo.order.dto.response.*;
import com.example.deligo.order.service.OrderService;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping
    public ResponseEntity<SaveResponse> saveOrder(HttpServletRequest request,
                                                  @Valid @RequestBody SaveRequest reqDto) {

        return ResponseEntity.ok().body(orderServ.saveOrder(getLoginUserId(request), reqDto));
    }

    @GetMapping("/user/{orderId}")
    public ResponseEntity<FindResponse> checkMyOrder(@PathVariable Long orderId, HttpServletRequest request) {
        return ResponseEntity.ok().body(orderServ.findUserOrder(orderId, getLoginUserId(request)));
    }

    @GetMapping("/owner/{orderId}")
    public ResponseEntity<FindStoreOrderResponse> checkStoreOrder(@PathVariable Long orderId, HttpServletRequest request) {

        return ResponseEntity.ok().body(orderServ.findStoreOrder(getLoginUserId(request), orderId));
    }

    @PatchMapping("/user/{orderId}")
    public ResponseEntity<OrderCancelResponse> cancelOrder(@PathVariable Long orderId, HttpServletRequest request) {
        Long loginUserId = getLoginUserId(request);
        return ResponseEntity.ok().body(orderServ.cancelOrder(loginUserId, orderId));
    }

    @PatchMapping("/owner/{orderId}/")
    public ResponseEntity<SetStatusResponse> editOrderStatus(@PathVariable Long orderId, @RequestBody EditRequest reqDto, HttpServletRequest request) {
        return ResponseEntity.ok().body(orderServ.setOrderStatus(getLoginUserId(request), orderId, reqDto.getOrderStatus()));
   }

    private static Long getLoginUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }
}
