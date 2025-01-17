package com.epam.learn.microcervices.songservice.dto;

import static com.epam.learn.microcervices.songservice.validation.ValidationConstants.SONG_METADATA_DURATION_PATTERN;
import static com.epam.learn.microcervices.songservice.validation.ValidationConstants.SONG_METADATA_ID_PATTERN;
import static com.epam.learn.microcervices.songservice.validation.ValidationConstants.SONG_METADATA_YEAR_PATTERN;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record SongMetadataDto(
        @NotNull
        @Pattern(regexp = SONG_METADATA_ID_PATTERN, message = "id must be numeric string")
        String id,

        @NotNull
        @Size(min = 1, max = 100)
        String name,

        @NotNull
        @Size(min = 1, max = 100)
        String artist,

        @NotNull
        @Size(min = 1, max = 100)
        String album,

        @NotNull
        @Pattern(regexp = SONG_METADATA_DURATION_PATTERN, message = "Duration must be in the format MM:SS")
        String duration,

        @NotNull
        @Pattern(regexp = SONG_METADATA_YEAR_PATTERN, message = "Year must be in a YYYY format")
        String year) {
}
