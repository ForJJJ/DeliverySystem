package com.forj.delivery.application.dto.request;

import java.util.List;
import java.util.UUID;

public record DriverAssignRequestDto(
        List<UUID> deliveryIds
) {}
