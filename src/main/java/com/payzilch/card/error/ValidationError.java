package com.payzilch.card.error;

public class ValidationError {
    private ErrorCode errorCode;
    private String property;
    private String errorMsg;

    public ValidationError() {}

    public ValidationError(ErrorCode errorCode, String property, String errorMsg) {
        this.errorCode = errorCode;
        this.property = property;
        this.errorMsg = errorMsg;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getProperty() {
        return property;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
