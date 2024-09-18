package com.forj.delivery_history.infrastructure.messaging;

public record DeliveryHistoryDeliveryCompleteMessage(
        Long deliveryAgentId
) {}
