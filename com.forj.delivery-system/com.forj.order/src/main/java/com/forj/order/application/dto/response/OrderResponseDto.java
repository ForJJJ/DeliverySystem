package com.forj.order.application.dto.response;

import com.forj.order.domain.enums.OrderStatusEnum;
import com.forj.order.domain.model.Order;

import java.util.UUID;

public record OrderResponseDto(
        UUID orderId,
        UUID requestCompanyId,
        UUID receivingCompanyId,
        UUID productId,
        Integer quantity,
        UUID deliveryId,
        OrderStatusEnum status
) {}
