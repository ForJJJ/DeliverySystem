package com.forj.delivery.infrastructure.messaging.message;

import java.util.UUID;

public record DeliveryDeliveryHistoryMessage(
        Long deliveryAgentId,
        UUID startHubId,
        UUID endHubId
) {}
