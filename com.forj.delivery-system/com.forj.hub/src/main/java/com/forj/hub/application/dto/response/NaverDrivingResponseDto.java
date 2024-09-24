package com.forj.hub.application.dto.response;

public record NaverDrivingResponseDto(
        String code,
        String message,
        String currentDateTime,
        RouteDto route
) {
}
