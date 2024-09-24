package com.forj.ai.infrastructure.dto;

import java.util.UUID;

public record DeliveryAgentDto(

        Long deliveryAgentId,
        UUID hubId

) {

}
