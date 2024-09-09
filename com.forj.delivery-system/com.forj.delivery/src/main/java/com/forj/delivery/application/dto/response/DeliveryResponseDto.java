package com.forj.delivery.application.dto.response;

import com.forj.delivery.domain.enums.DeliveryStatusEnum;
import com.forj.delivery.domain.model.Delivery;

import java.util.UUID;

public record DeliveryResponseDto (
         UUID deliveryId,

         UUID orderId,
         DeliveryStatusEnum status,
         UUID startHubId,
         UUID endHubId
    //     String endAddress,
    //     UUID deliveryAgentId,
    //     BigInteger userId,
    //     String slackId,
){

    public static DeliveryResponseDto fromDelivery(Delivery delivery) {
        return new DeliveryResponseDto(
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getStatus(),
                delivery.getStartHubId(),
                delivery.getEndHubId()
        );
    }
}
