package com.epam.learn.microcervices.resourceservice.collaborator;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClient;

import com.epam.learn.microcervices.resourceservice.collaborator.dto.SongMetadataSavedResponse;
import com.epam.learn.microcervices.resourceservice.collaborator.dto.SongsMetadataDeletedResponse;
import com.epam.learn.microcervices.resourceservice.configuration.RestClientRetryable;
import com.epam.learn.microcervices.resourceservice.dto.SongMetadataDto;
import com.epam.learn.microcervices.resourceservice.exception.ExternalServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class SongServiceCollaborator implements SongMetadataCollaborator {

    private final RestClient.Builder restClientBuilder;

    @Override
    @RestClientRetryable
    public void saveSongMetadata(@NonNull final SongMetadataDto songMetadataDto) {

        Assert.notNull(songMetadataDto, "songMetadataDto must not be null");

        restClientBuilder.build()
                .post()
                .body(songMetadataDto)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    log.error(
                            "Internal Song Service error occurred, while trying to save metadata for song id={}, statusCode: {}",
                            songMetadataDto.id(), response.getStatusCode());
                    throw new ExternalServiceException(
                            "Internal Song Service error occurred, while trying to save metadata for song id=%s"
                                    .formatted(songMetadataDto.id()));
                })
                .body(SongMetadataSavedResponse.class);
    }

    @Override
    @RestClientRetryable
    public void deleteSongsMetadata(@NonNull final List<String> ids) {

        Assert.notNull(ids, "ids must not be null");

        restClientBuilder.build()
                .delete()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("id", ids)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    log.error(
                            "Internal Song Service error occurred, while trying to delete metadata for song ids={}, statusCode: {}",
                            ids, response.getStatusCode());
                    throw new ExternalServiceException(
                            "Internal Song Service error occurred, while trying to delete metadata for song ids=%s".formatted(ids));
                })
                .body(SongsMetadataDeletedResponse.class);
    }
}
