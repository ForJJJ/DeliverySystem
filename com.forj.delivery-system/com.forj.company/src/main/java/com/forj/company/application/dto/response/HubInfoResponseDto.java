package com.forj.company.application.dto.response;

import java.util.UUID;

public record HubInfoResponseDto(
        UUID id,
        String name,
        String address,
        Double longitude,
        Double latitude
) {

    private HubInfoResponseDto(String name, String address) {
        this(null, name, address, null, null);
    }
}
