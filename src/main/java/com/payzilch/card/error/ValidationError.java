package com.payzilch.card.error;

public class ValidationError extends Error {
    private String property;

    public ValidationError() {}

    public ValidationError(ErrorCode errorCode, String property, String errorMsg) {
        super(errorCode, errorMsg);
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

}
