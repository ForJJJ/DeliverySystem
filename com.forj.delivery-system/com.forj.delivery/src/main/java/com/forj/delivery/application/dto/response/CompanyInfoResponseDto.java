package com.forj.delivery.application.dto.response;

import java.util.UUID;

public record CompanyInfoResponseDto(
        String id,
        String name,
        String userId,
        String managingHubId,
        String address,
        String companyType
){}
