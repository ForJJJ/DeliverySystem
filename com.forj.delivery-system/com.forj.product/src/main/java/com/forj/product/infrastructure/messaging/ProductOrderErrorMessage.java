package com.forj.product.infrastructure.messaging;

import java.util.UUID;

public record ProductOrderErrorMessage(
        UUID orderId,
        UUID requestCompanyId,
        UUID receivingCompanyId,
        UUID productId,
        Integer quantity,
        String errorMessage
) {}
