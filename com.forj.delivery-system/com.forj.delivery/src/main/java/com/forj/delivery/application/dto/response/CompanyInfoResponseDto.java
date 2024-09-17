package com.forj.delivery.application.dto.response;

import java.util.UUID;

public record CompanyInfoResponseDto(
        UUID companyId,
        String name,
        Long userId,
        UUID managingHubId,
        String address,
        String companyType
){}
