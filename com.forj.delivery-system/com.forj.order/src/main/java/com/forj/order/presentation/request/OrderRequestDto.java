package com.forj.order.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    private UUID receivingCompanyId;
    private UUID productId;
    private Integer quantity;
    private UUID deliveryId;
}
