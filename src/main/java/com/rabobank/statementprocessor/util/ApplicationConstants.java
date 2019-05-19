package com.rabobank.statementprocessor.util;

public enum ApplicationConstants {

    CLASS("com.rabobank.statementprocessor.model.Record"),
    ELEMENT_NAME("record");

    private String value;
    private ApplicationConstants(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
