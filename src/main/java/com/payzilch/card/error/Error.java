package com.payzilch.card.error;

public class Error {
    protected ErrorCode errorCode;
    protected String errorMsg;

    public Error() {}

    public Error(ErrorCode errorCode, String errorMsg) {
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
