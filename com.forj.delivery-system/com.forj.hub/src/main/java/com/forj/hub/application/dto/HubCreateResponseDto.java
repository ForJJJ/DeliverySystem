package com.forj.hub.application.dto;

public record HubCreateResponseDto(
        String id,
        String name,
        String address,
        double x,
        double y
) {
}
