package com.forj.hub.application.dto.request;

public record HubMovementRequestDto(
        String departureHubId,
        String arrivalHubId
) {
}
