package com.forj.product.application.dto.response;

import java.util.UUID;

public record CompanyInfoResponseDto(
        UUID id,
        String name,
        Long userId,
        UUID managingHubId,
        String address,
        String companyType
) {
}
