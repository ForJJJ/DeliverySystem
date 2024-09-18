package com.forj.hub.application.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.forj.hub.infrastructure.configuration.DurationSerializer;

import java.time.Duration;
import java.util.UUID;

public record HubMovementInfoResponseDto(
        UUID id,
        UUID departureHubId,
        UUID arrivalHubId,
        @JsonSerialize(using = DurationSerializer.class) Duration duration,
        String route
) {
}
