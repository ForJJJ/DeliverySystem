package com.forj.delivery.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDto {
    private UUID orderId;
    private UUID requestCompanyId;
    private UUID receivingCompanyId;
    private UUID deliveryId;
}
