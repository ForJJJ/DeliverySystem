package com.forj.delivery_agent.application.dto.response;

import com.forj.delivery_agent.domain.model.DeliveryAgent;
import com.forj.delivery_agent.domain.model.DeliveryAgentRole;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DeliveryAgentGetResponseDto(

        Long deliveryAgentId,
        UUID hubId,
        DeliveryAgentRole agentRole

) {

    public static DeliveryAgentGetResponseDto fromEntity(DeliveryAgent deliveryAgent) {
        return DeliveryAgentGetResponseDto.builder()
                .deliveryAgentId(deliveryAgent.getDeliveryAgentId())
                .hubId(deliveryAgent.getHubId())
                .agentRole(deliveryAgent.getAgentRole())
                .build();
    }

}
