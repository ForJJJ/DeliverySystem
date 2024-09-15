package com.forj.delivery_history.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryHistoryStatusEnum {
    READY("READY"),
    PROGRESS("PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELED");

    private final String status;
}
