package com.forj.order.infrastructure.messaging;

import com.forj.order.domain.enums.OrderStatusEnum;
import com.forj.order.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Getter
@NoArgsConstructor
public class OrderCreatedEvent implements Serializable {
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
