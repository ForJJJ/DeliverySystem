package com.forj.company.application.dto.response;

import com.forj.company.domain.model.CompanyType;

import java.util.UUID;

public record CompanyInfoResponseDto(
        UUID id,
        String name,
        Long userId,
        UUID managingHubId,
        String address,
        CompanyType companyType
) {
}
