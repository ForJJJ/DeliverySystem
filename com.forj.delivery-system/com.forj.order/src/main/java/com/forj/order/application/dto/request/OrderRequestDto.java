package com.forj.order.application.dto.request;

import java.util.UUID;

public record OrderRequestDto (
        UUID requestCompanyId,
        UUID receivingCompanyId,
        UUID productId,
        Integer quantity
){}

