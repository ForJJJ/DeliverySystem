package com.forj.auth.application.dto.response;

import com.forj.auth.domain.model.DeliveryAgentRole;
import com.forj.auth.domain.model.DeliveryAgent;
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
