package com.example.deligo.order.controller;

import com.example.deligo.common.annotation.UserId;
import com.example.deligo.order.dto.request.OrderEditRequest;
import com.example.deligo.order.dto.request.OrderSaveRequest;
import com.example.deligo.order.dto.response.*;
import com.example.deligo.order.service.orderServ.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderServ;

    @PostMapping
    public ResponseEntity<OrderSaveResponse> saveOrder(@UserId Long userId,
                                                       @Valid @RequestBody OrderSaveRequest reqDto) {

        return ResponseEntity.ok().body(orderServ.saveOrder(userId, reqDto));
    }

    @GetMapping("/user/{orderId}")
    public ResponseEntity<OrderFindResponse> checkMyOrder(@PathVariable Long orderId, @UserId Long userId) {
        return ResponseEntity.ok().body(orderServ.findUserOrder(userId, orderId));
    }

    @GetMapping("/owner/{orderId}")
    public ResponseEntity<FindStoreOrderResponse> checkStoreOrder(@PathVariable Long orderId, @UserId Long userId) {

        return ResponseEntity.ok().body(orderServ.findStoreOrder(userId, orderId));
    }

    @PatchMapping("/user/{orderId}")
    public ResponseEntity<OrderCancelResponse> cancelOrder(@PathVariable Long orderId, @UserId Long userId) {
        return ResponseEntity.ok().body(orderServ.cancelOrder(userId, orderId));
    }

    @PatchMapping("/owner/{orderId}")
    public ResponseEntity<SetOrderStatusResponse> editOrderStatus(@PathVariable Long orderId, @RequestBody OrderEditRequest reqDto, @UserId Long userId) {
        return ResponseEntity.ok().body(orderServ.setOrderStatus(userId, orderId, reqDto.getOrderStatus()));
   }
}
