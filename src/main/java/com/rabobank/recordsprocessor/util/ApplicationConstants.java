package com.rabobank.recordsprocessor.util;

public enum ApplicationConstants {

    CLASS("com.rabobank.recordsprocessor.model.Record"),
    ELEMENT_NAME("record");

    private String value;
    private ApplicationConstants(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
