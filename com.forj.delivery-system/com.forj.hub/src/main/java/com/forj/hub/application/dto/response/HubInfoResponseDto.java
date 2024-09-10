package com.forj.hub.application.dto.response;

public record HubInfoResponseDto(
        String id,
        String name,
        String address,
        Double x,
        Double y
) {

    private HubInfoResponseDto(String name, String address) {
        this(null, name, address, null, null);
    }

    public static HubInfoResponseDto forPublicResponse(String name, String address) {
        return new HubInfoResponseDto(name, address);
    }

    public static HubInfoResponseDto forPrivateResponse(String id, String name, String address, double x, double y) {
        return new HubInfoResponseDto(id, name, address, x, y);
    }
}
