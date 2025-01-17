package com.epam.learn.microcervices.resourceservice.dto;

import lombok.Builder;

@Builder
public record SongMetadataDto(
        String id,
        String name,
        String artist,
        String album,
        String duration,
        String year) {
}
