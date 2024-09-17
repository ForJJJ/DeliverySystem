package com.forj.order.infrastructure.messaging.message;

import java.util.UUID;


public record OrderProductMessage(
        UUID orderId,
        UUID requestCompanyId,
        UUID receivingCompanyId,
        UUID productId,
        Integer quantity
) {}
