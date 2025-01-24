package com.epam.learn.microcervices.resourceservice.exception;

import org.springframework.lang.NonNull;

public class SongServiceClientException extends ApplicationException {

    public SongServiceClientException(String message) {

        super(message);
    }

    @Override
    @NonNull
    public ErrorProblemTypes getErrorProblemType() {

        return ErrorProblemTypes.CLIENT_SIDE_ERROR;
    }


}
