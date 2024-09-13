package com.forj.company.application.dto.request;

public record CompanyRequestDto(
        String name,
        String address,
        String companyType
) {
}
