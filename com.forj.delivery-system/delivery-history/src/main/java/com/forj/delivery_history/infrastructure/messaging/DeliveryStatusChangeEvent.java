package com.forj.delivery_history.infrastructure.messaging;

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
