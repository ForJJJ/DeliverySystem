package com.forj.delivery.infrastructure.messaging.message;

import java.util.UUID;


public record DeliveryProductMessage(
        UUID orderId,
        UUID requestCompanyId,
        UUID receivingCompanyId
) {}
