package com.epam.learn.microcervices.resourceservice.exception;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ProblemDetails {

    private String errorMessage;
    private Map<String, String> details;
    private String errorCode;

}
