package com.forj.company.application.dto.request;

import com.forj.company.domain.model.CompanyType;

public record CompanyRequestDto(
        String name,
        String address,
        CompanyType companyType
) {
}
