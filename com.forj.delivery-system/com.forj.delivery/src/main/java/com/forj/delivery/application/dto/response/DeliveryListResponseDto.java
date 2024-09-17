package com.forj.delivery.application.dto.response;

import org.springframework.data.domain.Page;

public record DeliveryListResponseDto (
        Page<DeliveryResponseDto> deliveryList
){}
