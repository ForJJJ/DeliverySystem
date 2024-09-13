package com.forj.delivery_agent.application.dto.request;


import com.forj.delivery_agent.domain.model.DeliveryAgentRole;

import java.util.UUID;

public record DeliveryAgentRequestDto(

        UUID hubId,
        DeliveryAgentRole role

) {

}
