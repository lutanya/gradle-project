package com.epam.learn.microcervices.songservice.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetails> onMethodArgumentNotValidException(
            @NonNull final MethodArgumentNotValidException exception) {

        log.info("MethodArgumentNotValidException was detected: ", exception);
        final Map<String, String> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, this::fetchFieldErrorMessage));

        return ResponseEntity.badRequest()
                .body(ProblemDetails.builder()
                        .errorMessage("Validation error")
                        .details(validationErrors)
                        .errorCode("400")
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetails> onConstraintViolationException(
            @NonNull final ConstraintViolationException exception) {

        log.info("ConstraintViolationException was detected: ", exception);
        final Map<String, String> validationErrors = exception.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage));

        return ResponseEntity.badRequest()
                .body(ProblemDetails.builder()
                        .errorMessage("Validation error")
                        .details(validationErrors)
                        .errorCode("400")
                        .build());
    }

    @ExceptionHandler(SongMetadataAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ProblemDetails> onSongMetadataAlreadyExistsException(
            @NonNull final SongMetadataAlreadyExistsException exception) {

        log.warn("SongMetadataAlreadyExistsException was detected: ", exception);
        return ResponseEntity
                .status(HttpStatus.CONFLICT.value())
                .body(ProblemDetails.builder()
                        .errorMessage(exception.getMessage())
                        .errorCode("409")
                        .build());
    }

    @ExceptionHandler(SongMetadataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetails> onSongMetadataAlreadyExistsException(
            @NonNull final SongMetadataNotFoundException exception) {

        log.warn("SongMetadataNotFoundException was detected: ", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(ProblemDetails.builder()
                        .errorMessage(exception.getMessage())
                        .errorCode("404")
                        .build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ProblemDetails> globalExceptionHandler(@NonNull final Exception exception) {

        log.error("Exception was detected: ", exception);
        return ResponseEntity.internalServerError()
                .body(ProblemDetails.builder()
                        .errorMessage(exception.getMessage())
                        .errorCode("500")
                        .build());
    }

    public String fetchFieldErrorMessage(final FieldError fieldError) {

        return StringUtils.hasLength(fieldError.getDefaultMessage())
                ? fieldError.getDefaultMessage()
                : "field validation is failed";
    }


}
