package com.forj.hub.application.dto.response;

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

    public HubInfoResponseDto(UUID id, String name, String address, Double longitude, Double latitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public static HubInfoResponseDto forPublicResponse(String name, String address) {
        return new HubInfoResponseDto(name, address);
    }

    public static HubInfoResponseDto forPrivateResponse(UUID id, String name, String address, Double longitude, Double latitude) {
        return new HubInfoResponseDto(id, name, address, longitude, latitude);
    }
}
