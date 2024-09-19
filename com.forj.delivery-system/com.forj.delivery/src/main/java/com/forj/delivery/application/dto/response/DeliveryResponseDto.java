package com.forj.delivery.application.dto.response;

import com.forj.delivery.domain.enums.DeliveryStatusEnum;
import com.forj.delivery.domain.model.Delivery;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DeliveryResponseDto(
        UUID deliveryId,
        UUID orderId,
        DeliveryStatusEnum status,
        UUID startHubId,
        UUID endHubId,
        String endAddress,
        Long deliveryAgentId,
        Long userId,
        String slackId
) {

    public static DeliveryResponseDto fromEntity(Delivery delivery) {
        return DeliveryResponseDto.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(delivery.getOrderId())
                .status(delivery.getStatus())
                .startHubId(delivery.getStartHubId())
                .endHubId(delivery.getEndHubId())
                .endAddress(delivery.getEndAddress())
                .deliveryAgentId(delivery.getDeliveryAgentId())
                .userId(delivery.getUserId())
                .slackId(delivery.getSlackId())
                .build();
    }

}
