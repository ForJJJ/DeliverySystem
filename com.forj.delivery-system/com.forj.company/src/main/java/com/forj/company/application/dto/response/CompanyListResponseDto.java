package com.forj.company.application.dto.response;

import java.util.List;

public record CompanyListResponseDto(
        List<CompanyInfoResponseDto> companyList
) {
}
