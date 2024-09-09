package com.forj.delivery.infrastructure.messaging;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Getter
@NoArgsConstructor
public class OrderCreatedEvent {
    private UUID orderId;
    private UUID requestCompanyId;
    private UUID receivingCompanyId;
    private UUID deliveryId;
}
