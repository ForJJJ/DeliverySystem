package com.forj.delivery.infrastructure.messaging.message;

import java.util.UUID;

public record DeliveryOrderMessage(
        UUID deliveryId,
        UUID orderId
) {}
