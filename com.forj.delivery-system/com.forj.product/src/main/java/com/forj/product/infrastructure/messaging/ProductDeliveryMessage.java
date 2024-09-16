package com.forj.product.infrastructure.messaging;

public record ProductDeliveryMessage(
        String productId,
        String companyId,
        String managingHubId,
        Integer quantity,
        String errorMessage
) {
}
