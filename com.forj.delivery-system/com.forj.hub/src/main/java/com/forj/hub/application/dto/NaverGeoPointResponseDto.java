package com.forj.hub.application.dto;

import com.forj.hub.application.dto.AddressDto;

import java.util.List;

public record NaverGeoPointResponseDto(
        String status,
        String errorMessage,
        List<AddressDto> addresses
) {
}
