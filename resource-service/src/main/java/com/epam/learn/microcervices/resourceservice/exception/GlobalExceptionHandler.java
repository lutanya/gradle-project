package com.epam.learn.microcervices.resourceservice.exception;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.epam.learn.microcervices.resourceservice.exception.ProblemDetails.ProblemDetailsBuilder;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ProblemDetails> onApplicationException(@NonNull final ApplicationException exception) {

        final ErrorProblemTypes errorProblemType = exception.getErrorProblemType();
        final HttpStatus httpStatus = HttpStatus.valueOf(errorProblemType.getCode());
        return new ResponseEntity<>(buildProblemDetails(errorProblemType).build(), httpStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetails> onMethodArgumentTypeMismatchException(
            @NonNull final MethodArgumentTypeMismatchException exception) {

        log.info("MethodArgumentTypeMismatchException was detected: ", exception);
        final Map<String, String> validationErrors = Map.of(
                Objects.requireNonNull(exception.getPropertyName()),
                exception.getMessage());

        return ResponseEntity.badRequest().body(buildValidationError(validationErrors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetails> onMethodArgumentNotValidException(
            @NonNull final MethodArgumentNotValidException exception) {

        log.info("MethodArgumentNotValidException was detected: ", exception);
        final Map<String, String> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, this::fetchFieldErrorMessage));

        return ResponseEntity.badRequest().body(buildValidationError(validationErrors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetails> onConstraintViolationException(
            @NonNull final ConstraintViolationException exception) {

        log.info("ConstraintViolationException was detected: ", exception);
        final Map<String, String> validationErrors = exception.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage));

        return ResponseEntity.badRequest().body(buildValidationError(validationErrors));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ProblemDetails> onHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException exception) {

        log.info("HttpMediaTypeNotSupportedException was detected: ", exception);
        return ResponseEntity.badRequest()
                .body(ProblemDetails.builder()
                        .errorMessage(exception.getMessage())
                        .errorCode("400")
                        .build());
    }

    @ExceptionHandler(value = DataAccessException.class)

    public ResponseEntity<ProblemDetails> onDataAccessException(@NonNull final DataAccessException exception) {

        log.error("DataAccessException was detected: ", exception);
        return ResponseEntity.internalServerError()
                .body(buildProblemDetails(ErrorProblemTypes.INTERNAL_SERVER_ERROR).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetails> onException(@NonNull final Exception exception) {

        log.error("Exception was detected: ", exception);
        return ResponseEntity.internalServerError()
                .body(buildProblemDetails(ErrorProblemTypes.INTERNAL_SERVER_ERROR).build());
    }

    private ProblemDetails buildValidationError(final Map<String, String> validationErrors) {

        return buildProblemDetails(ErrorProblemTypes.REQUEST_VALIDATION_ERROR)
                .details(validationErrors)
                .build();
    }

    private ProblemDetailsBuilder buildProblemDetails(final ErrorProblemTypes errorProblemType) {

        return ProblemDetails.builder()
                .errorMessage(errorProblemType.getMessage())
                .errorCode(String.valueOf(errorProblemType.getCode()));
    }

    public String fetchFieldErrorMessage(final FieldError fieldError) {

        return StringUtils.hasLength(fieldError.getDefaultMessage())
                ? fieldError.getDefaultMessage()
                : "field validation is failed";
    }

}
