package com.forj.delivery_history.application.dto;

import java.time.Duration;
import java.util.UUID;

public record HubMovementInfoResponseDto(
        UUID id,
        UUID departureHubId,
        UUID arrivalHubId,
        Duration duration,
        String route
) {}
