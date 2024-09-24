package com.forj.order.infrastructure.messaging.message;

import java.util.UUID;

public record OrderProductCancelMessage (
        UUID orderId,
        UUID productId,
        Integer quantity
){}
