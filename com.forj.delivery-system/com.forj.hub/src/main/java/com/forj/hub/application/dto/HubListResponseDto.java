package com.forj.hub.application.dto;

import java.util.List;

public record HubListResponseDto(
        List<HubInfoResponseDto> hubList
) {
}
