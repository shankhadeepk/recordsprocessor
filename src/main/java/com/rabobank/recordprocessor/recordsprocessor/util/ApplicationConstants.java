package com.rabobank.recordprocessor.recordsprocessor.util;

public enum ApplicationConstants {

    CLASS("com.rabobank.recordprocessor.recordsprocessor.model.Record"),
    ELEMENT_NAME("record");

    private String value;
    private ApplicationConstants(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
