package com.epam.learn.microcervices.resourceservice.exception;

import org.springframework.lang.NonNull;

public class SongMetadataInvalidException extends ApplicationException {

    public SongMetadataInvalidException(String message) {

        super(message);
    }

    @Override
    @NonNull
    public ErrorProblemTypes getErrorProblemType() {

        return ErrorProblemTypes.METADATA_INVALID_ERROR;
    }
}
