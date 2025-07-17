package com.example.demo.model.response;

public enum ErrorMessage {
    MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
    RECORD_ALREADY_EXISTSC("Record already exists"), 
    INTERNAL_SERVER_ERRORC("Internal server error"),
    NO_RECORD_FOUND("Record with provided id is not found"), 
    AUTHENTICATION_FAILEDC("Authentication failed"),
    OULD_NOT_UPDATE_RECORDC("Could not update record"), 
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_ADDRESS_NOT_VERIFIEDC("Email address could not be verified");

    private String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
