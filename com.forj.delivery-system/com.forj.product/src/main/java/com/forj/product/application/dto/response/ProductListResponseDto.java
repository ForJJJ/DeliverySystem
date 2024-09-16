package com.forj.product.application.dto.response;

import org.springframework.data.domain.Page;

public record ProductListResponseDto(
        Page<ProductInfoResponseDto> productList
) {
}
