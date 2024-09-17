package com.forj.order.infrastructure.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusChangeEvent {
    private UUID deliveryId;
    private UUID orderId;
}
