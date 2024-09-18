package com.forj.ai.domain.model;

public enum RequestType {

    FOR_COMPANY_DELIVERY_AGENT("FOR_COMPANY_DELIVERY_AGENT"),
    FOR_HUB_DELIVERY_AGENT("FOR_HUB_DELIVERY_AGENT");

    private final String value;

    RequestType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
