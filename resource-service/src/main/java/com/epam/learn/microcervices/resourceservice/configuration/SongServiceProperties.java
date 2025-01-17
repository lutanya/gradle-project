package com.epam.learn.microcervices.resourceservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Component
@Data
@Validated
@ConfigurationProperties(prefix = "song-service")
public class SongServiceProperties {

    @NotBlank
    private String baseUrl;
    private Integer readTimeout;
    private Integer connectionTimeout;
}
