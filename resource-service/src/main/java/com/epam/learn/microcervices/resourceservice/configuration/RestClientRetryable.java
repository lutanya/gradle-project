package com.epam.learn.microcervices.resourceservice.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.2))
public @interface RestClientRetryable {
}
