package com.forj.company.application.dto.response;

public record CompanyInfoResponseDto(
        String id,
        String name,
        String userId,
        String managingHubId,
        String address,
        String companyType
) {
}
