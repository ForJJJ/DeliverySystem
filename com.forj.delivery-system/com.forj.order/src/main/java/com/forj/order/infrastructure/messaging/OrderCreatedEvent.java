package com.forj.order.infrastructure.messaging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;


@Getter
@NoArgsConstructor
public class OrderCreatedEvent {
    private UUID orderId;
    private UUID requestCompanyId;
    private UUID receivingCompanyId;
    private UUID deliveryId;

    public OrderCreatedEvent(UUID orderId, UUID requestCompanyId, UUID receivingCompanyId, UUID deliveryId) {
        this.orderId = orderId;
        this.requestCompanyId = requestCompanyId;
        this.receivingCompanyId = receivingCompanyId;
        this.deliveryId = deliveryId;
    }

}
