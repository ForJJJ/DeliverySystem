package com.forj.product.application.dto.request;

public record ProductRequestDto(
        String name,
        String companyId,
        Integer quantity
) {
}
