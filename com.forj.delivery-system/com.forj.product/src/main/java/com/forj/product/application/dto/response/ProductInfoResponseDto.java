package com.forj.product.application.dto.response;

public record ProductInfoResponseDto(
        String id,
        String name,
        String companyId,
        String managingHubId,
        Integer quantity
) {
}
