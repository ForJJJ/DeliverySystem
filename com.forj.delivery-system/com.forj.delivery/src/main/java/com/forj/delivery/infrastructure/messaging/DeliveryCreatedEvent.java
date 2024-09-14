package com.forj.delivery.infrastructure.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCreatedEvent {
    private UUID deliveryAgentId;
    private UUID startHubId;
    private UUID endHubId;
}
