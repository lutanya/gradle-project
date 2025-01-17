package com.epam.learn.microcervices.resourceservice.exception;

import org.springframework.lang.NonNull;

public class InternalServiceException extends ApplicationException {

    public InternalServiceException(String message) {

        super(message);
    }

    public InternalServiceException(String message, Throwable cause) {

        super(message, cause);
    }

    @Override
    @NonNull
    public ErrorProblemTypes getErrorProblemType() {

        return ErrorProblemTypes.INTERNAL_SERVER_ERROR;
    }
}
