package com.epam.learn.microcervices.resourceservice.exception;

import org.springframework.lang.NonNull;

public abstract class ApplicationException extends RuntimeException {

    private ErrorProblemTypes errorProblemType;

    public ApplicationException() {

        super();
    }

    public ApplicationException(Throwable cause) {

        super(cause);
    }

    public ApplicationException(String message) {

        super(message);
    }

    public ApplicationException(String message, Throwable cause) {

        super(message, cause);
    }

    @NonNull
    abstract public ErrorProblemTypes getErrorProblemType();
}
