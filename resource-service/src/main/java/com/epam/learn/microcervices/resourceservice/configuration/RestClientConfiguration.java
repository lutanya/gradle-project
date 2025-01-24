package com.epam.learn.microcervices.resourceservice.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient.Builder restClientBuilder(
            final SongServiceProperties properties,
            final HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {

        Assert.notNull(properties, "properties must not be null");
        Assert.notNull(httpComponentsClientHttpRequestFactory,"httpComponentsClientHttpRequestFactory must not be null");

        return RestClient
                .builder()
                .requestFactory(httpComponentsClientHttpRequestFactory)
                .baseUrl(properties.getBaseUrl());
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(final SongServiceProperties properties) {

        Assert.notNull(properties, "properties must not be null");

        final var factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(properties.getConnectionTimeout());
        factory.setReadTimeout(properties.getReadTimeout());
        return factory;
    }
}
