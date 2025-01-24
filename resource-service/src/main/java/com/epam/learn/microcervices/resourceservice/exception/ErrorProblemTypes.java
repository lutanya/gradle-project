package com.epam.learn.microcervices.resourceservice.exception;

import lombok.Getter;

@Getter
public enum ErrorProblemTypes {
    REQUEST_VALIDATION_ERROR(
            "Request validation failed",
            "The request is invalid. Check API specification to verify if all required request elements are " +
                    "provided and correct.",
            400
    ),
    INTERNAL_SERVER_ERROR(
            "Unexpected internal server error",
            "Unexpected internal server error occurred. Please contact service administrator for more details.",
            500
    ),
    EXTERNAL_SERVER_ERROR(
            "Unexpected external error",
            "Unexpected external server error occurred. Please contact service administrator for more details.",
            500
    ),
    CLIENT_SIDE_ERROR(
            "Unexpected client side error",
            "Unexpected client side error occurred. Please contact service administrator for more details.",
            400
    ),
    NOT_FOUND_ERROR(
            "Resource not found",
            "Requested resource not found",
            404
    ),
    METADATA_INVALID_ERROR(
            "Song metadata invalid",
            "Please check song metadata details.",
            400
    );

    private final String title;
    private final String message;
    private final int code;

    ErrorProblemTypes(final String errorTitle, final String errorMessage, final int errorCode) {

        this.title = errorTitle;
        this.message = errorMessage;
        this.code = errorCode;
    }
}
