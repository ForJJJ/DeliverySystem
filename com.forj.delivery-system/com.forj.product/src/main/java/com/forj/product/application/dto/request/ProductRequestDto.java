package com.forj.product.application.dto.request;

import java.util.UUID;

public record ProductRequestDto(
        String name,
        UUID companyId,
        Integer quantity
) {
}
