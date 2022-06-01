package com.ai.st.microservice.supplies.exceptions;

public class FeignMicroserviceException extends Exception {

    private static final long serialVersionUID = 3230476461126206019L;

    private String messageError;

    public FeignMicroserviceException(String message) {
        super();
        this.messageError = message;
    }

    public String getMessageError() {
        return messageError;
    }

    @Override
    public String getMessage() {
        return this.getMessageError();
    }

}
