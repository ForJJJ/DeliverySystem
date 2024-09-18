package com.forj.delivery.application.dto.response;

import com.forj.delivery.domain.enums.DeliveryStatusEnum;

import java.util.UUID;

public record DeliveryResponseDto (
         UUID deliveryId,
         UUID orderId,
         DeliveryStatusEnum status,
         UUID startHubId,
         UUID endHubId,
         String endAddress,
         Long deliveryAgentId,
         Long userId,
         String slackId
){}
