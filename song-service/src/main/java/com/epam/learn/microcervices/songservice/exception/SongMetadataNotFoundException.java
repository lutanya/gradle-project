package com.epam.learn.microcervices.songservice.exception;

public class SongMetadataNotFoundException extends RuntimeException {

    public SongMetadataNotFoundException(String message) {

        super(message);
    }
}
