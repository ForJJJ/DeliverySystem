package com.forj.auth.domain.model;

public enum DeliveryAgentRole {

    HUBDELIVERY("HUBDELIVERY"),
    COMPANYDELIVERY("COMPANYDELIVERY");

    private final String value;

    DeliveryAgentRole(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
