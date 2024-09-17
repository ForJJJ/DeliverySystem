package com.forj.delivery.infrastructure.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusChangeEvent {
    private UUID deliveryAgentId;
}
