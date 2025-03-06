package com.example.deligo.order.dto.response;

import com.example.deligo.order.entity.Order;
import com.example.deligo.order.entity.OrderItem;
import com.example.deligo.order.entity.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class SaveResponse {
    private Long userId;
    private Long storeId;
    private Map<Long, Integer> menus;
    private String deliverAddress;
    private String storeComment;
    private String riderComment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus orderStatus;

    public SaveResponse(Order order, Map<Long, Integer> menus) {
        this.menus = menus;
        this.createdAt = order.getCreatedAt();
        this.deliverAddress = order.getDeliverAddress();
        this.storeId = order.getStore().getId();
        this.userId = order.getUser().getId();
        this.riderComment = order.getRiderComment();
        this.storeComment = order.getStoreComment();
        this.updatedAt = order.getUpdatedAt();
        this.orderStatus = order.getStatus();
    }
}
