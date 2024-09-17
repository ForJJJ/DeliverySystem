package com.forj.order.application.dto.response;

import org.springframework.data.domain.Page;

public record OrderListResponseDto (
        Page<OrderResponseDto> orderList
){}
