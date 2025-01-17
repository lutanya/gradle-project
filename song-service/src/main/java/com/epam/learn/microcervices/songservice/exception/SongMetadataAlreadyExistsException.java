package com.epam.learn.microcervices.songservice.exception;

public class SongMetadataAlreadyExistsException extends RuntimeException {

    public SongMetadataAlreadyExistsException(String message) {

        super(message);
    }
}
