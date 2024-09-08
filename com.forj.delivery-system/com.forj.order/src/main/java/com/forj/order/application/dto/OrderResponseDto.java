package com.forj.order.application.dto;

import com.forj.order.domain.enums.OrderEnum;
import com.forj.order.domain.model.Order;

import java.util.UUID;

public record OrderResponseDto(
        UUID orderId,
        UUID requestCompanyId,
        UUID receivingCompanyId,
        UUID productId,
        Integer quantity,
        UUID deliveryId,
        OrderEnum status
) {
    public static OrderResponseDto fromOrder(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getRequestCompanyId(),
                order.getReceivingCompanyId(), // 수정된 부분
                order.getProductId(),
                order.getQuantity(),
                order.getDeliveryId(),
                order.getStatus()
        );
    }
}
