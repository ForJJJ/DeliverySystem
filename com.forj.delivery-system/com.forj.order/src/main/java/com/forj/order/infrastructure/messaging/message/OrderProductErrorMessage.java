package com.forj.order.infrastructure.messaging.message;

public record OrderProductErrorMessage(
        String orderId,
        String productId,
        String companyId,
        String managingHubId,
        Integer quantity,
        String errorMessage
){}
