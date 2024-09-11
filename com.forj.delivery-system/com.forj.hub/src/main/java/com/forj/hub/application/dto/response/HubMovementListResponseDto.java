package com.forj.hub.application.dto.response;

import org.springframework.data.domain.Page;

public record HubMovementListResponseDto(
        Page<HubMovementInfoResponseDto> hubMovementList
) {
}
