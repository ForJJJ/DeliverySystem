package com.forj.company.application.dto.response;

import java.util.List;

public record HubListResponseDto(
        List<HubInfoResponseDto> hubList
) {
}
