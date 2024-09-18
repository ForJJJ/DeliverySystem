package com.forj.hub.application.dto.request;

import java.util.UUID;

public record HubMovementRequestDto(
        UUID departureHubId,
        UUID arrivalHubId
) {
}
