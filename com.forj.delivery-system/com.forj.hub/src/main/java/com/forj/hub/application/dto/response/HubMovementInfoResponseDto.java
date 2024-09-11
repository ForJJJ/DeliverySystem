package com.forj.hub.application.dto.response;

public record HubMovementInfoResponseDto(
        String id,
        String departureHubId,
        String arrivalHubId,
        String duration,
        String route
) {
}
