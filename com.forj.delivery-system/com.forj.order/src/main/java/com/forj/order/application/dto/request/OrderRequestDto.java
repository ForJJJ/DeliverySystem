package com.forj.order.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    private UUID receivingCompanyId;
    private UUID productId;
    private Integer quantity;
    private UUID deliveryId;
}
