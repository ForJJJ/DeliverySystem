package com.forj.delivery.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatusEnum {
    READY("READY"),
    PROGRESS("PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String status;
}
