package com.forj.order.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusEnum {
    PROGRESS("PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String status;
}
