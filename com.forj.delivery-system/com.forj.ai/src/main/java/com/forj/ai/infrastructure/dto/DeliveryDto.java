package com.forj.ai.infrastructure.dto;


import java.util.UUID;

public record DeliveryDto(

        UUID deliveryId,
        String status,
        String startHubId,
        String endAddress,
        Long userId

) {

}
