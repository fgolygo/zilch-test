package com.payzilch.card.error;

public enum ErrorCode {
    ZILCH_001("Must not be null"),
    ZILCH_002("Must be between 3-30 characters"),
    ZILCH_003("Allowed values: [%s]"),
    ZILCH_004("Requested resource has not been found"),
    ZILCH_005("Invalid json structure"),
    ZILCH_006("There are unrecognized fields in the body"),
    ZILCH_007("Input should be a json object instead of an array"),
    ZILCH_999("Unknown");

    private String errorMsg;

    ErrorCode(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
