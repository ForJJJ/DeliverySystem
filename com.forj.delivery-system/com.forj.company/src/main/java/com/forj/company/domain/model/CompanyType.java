package com.forj.company.domain.model;

import java.util.Arrays;

public enum CompanyType {
    PRODUCER,
    RECEIVER;

    public static CompanyType fromString(String value) {
        return Arrays.stream(CompanyType.values())
                .filter(type -> type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown company status: " + value));
    }

}
