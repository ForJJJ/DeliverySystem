package com.forj.product.application.dto.response;

import java.util.UUID;

public record ProductInfoResponseDto(
        UUID id,
        String name,
        UUID companyId,
        UUID managingHubId,
        Integer quantity
) {
}
