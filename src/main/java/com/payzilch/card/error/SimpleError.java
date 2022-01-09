package com.payzilch.card.error;

public class SimpleError {
    private ErrorCode errorCode;
    private String errorMsg;

    public SimpleError() {}

    public SimpleError(ErrorCode errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
