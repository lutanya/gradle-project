package com.epam.learn.microcervices.resourceservice.exception;

import org.springframework.lang.NonNull;

public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String message) {

        super(message);
    }

    @Override
    @NonNull
    public ErrorProblemTypes getErrorProblemType() {

        return ErrorProblemTypes.NOT_FOUND_ERROR;
    }
}
