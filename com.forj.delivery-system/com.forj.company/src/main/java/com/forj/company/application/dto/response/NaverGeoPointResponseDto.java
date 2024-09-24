package com.forj.company.application.dto.response;

import java.util.List;

public record NaverGeoPointResponseDto(
        String status,
        String errorMessage,
        List<AddressDto> addresses
) {
}
