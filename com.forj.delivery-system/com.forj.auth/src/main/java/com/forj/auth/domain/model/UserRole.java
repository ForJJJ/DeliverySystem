package com.forj.auth.domain.model;

public enum UserRole {

    MASTER("MASTER"),
    HUBMASTER("HUBMASTER"),
    DELIVERYAGENT("DELIVERYAGENT"),
    HUBCOMPANY("HUBCOMPANY");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
