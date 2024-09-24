package com.forj.product.application.dto.response;

import java.util.UUID;

public record HubInfoResponseDto(
        UUID id,
        String name,
        String address,
        Double longitude,
        Double latitude
) {
}
