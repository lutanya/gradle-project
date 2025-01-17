package com.epam.learn.microcervices.resourceservice.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidMP3Validator.class)
public @interface ValidMP3 {
    String message() default "CSV string format is invalid or exceeds length restrictions";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
