package com.forj.auth.application.dto.request;

import com.forj.auth.domain.model.DeliveryAgentRole;

import java.util.UUID;

public record DeliveryAgentRequestDto(

        UUID hubId,
        DeliveryAgentRole role

) {

}
