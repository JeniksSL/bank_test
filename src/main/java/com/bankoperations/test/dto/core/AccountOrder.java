package com.bankoperations.test.dto.core;

public enum AccountOrder {
    NAME("name"),
    BIRTH_DATE("birthDate"),
    INITIAL_DEPOSIT("initialDeposit"),
    BALANCE("balance");
    private final String property;

    AccountOrder(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
