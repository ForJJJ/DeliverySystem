package com.forj.hub.application.dto.response;

import java.util.List;

public record NaverGeoPointResponseDto(
        String status,
        String errorMessage,
        List<AddressDto> addresses
) {
}
