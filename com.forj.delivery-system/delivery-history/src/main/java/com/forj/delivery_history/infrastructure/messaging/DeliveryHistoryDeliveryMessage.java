package com.forj.delivery_history.infrastructure.messaging;

import java.util.UUID;

public record DeliveryHistoryDeliveryMessage(
    Long deliveryAgentId,
    UUID startHubId,
    UUID endHubId,
    String role
) {}