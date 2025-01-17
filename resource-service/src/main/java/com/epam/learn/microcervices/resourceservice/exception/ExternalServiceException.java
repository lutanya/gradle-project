package com.epam.learn.microcervices.resourceservice.exception;

import org.springframework.lang.NonNull;

public class ExternalServiceException extends ApplicationException {

    public ExternalServiceException(String message) {

        super(message);
    }

    @Override
    @NonNull
    public ErrorProblemTypes getErrorProblemType() {

        return ErrorProblemTypes.EXTERNAL_SERVER_ERROR;
    }
}