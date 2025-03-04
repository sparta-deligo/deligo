package com.example.deligo.order.dto.response;

import com.example.deligo.order.entity.Order;
import com.example.deligo.order.entity.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class SaveResponse {
    private Long userId;
    private Long storeId;
    private List<Long> menuId;
    private String deliverAddress;
    private String storeComment;
    private String riderComment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SaveResponse(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        this.menuId = orderItems.stream()
                .map(orderItem -> orderItem.getMenu().getId())
                .toList();
        this.createdAt = order.getCreatedAt();
        this.deliverAddress = order.getDeliverAddress();
        this.storeId = order.getStore().getId();
        this.userId = order.getUser().getId();
        this.riderComment = order.getRiderComment();
        this.storeComment = order.getStoreComment();
        this.updatedAt = order.getUpdatedAt();
    }
}
