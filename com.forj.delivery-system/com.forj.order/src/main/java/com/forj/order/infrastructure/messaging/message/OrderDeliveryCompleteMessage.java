package com.forj.order.infrastructure.messaging.message;

import java.util.UUID;

public record OrderDeliveryCompleteMessage (
        UUID orderId,
        UUID deliveryId
){}
