package com.epam.learn.microcervices.resourceservice.service.converter;

import java.time.Duration;

import org.apache.tika.metadata.Metadata;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.epam.learn.microcervices.resourceservice.dto.SongMetadataDto;

@Component
public class SongMetadataConverter {

    private static final String SONG_ALBUM_METADATA_NAME = "xmpDM:album";
    private static final String SONG_ARTIST_METADATA_NAME = "xmpDM:albumArtist";
    private static final String SONG_DURATION_FORMAT = "%02d:%02d";
    private static final String SONG_DURATION_METADATA_NAME = "xmpDM:duration";
    private static final String SONG_TITLE_METADATA_NAME = "dc:title";
    private static final String SONG_YEAR_METADATA_NAME = "xmpDM:releaseDate";
    private static final String UNKNOWN_METADATA_VALUE = "unknown";
    private static final String UNKNOWN_SONG_DURATION_METADATA_VALUE = "00:00";

    @NonNull
    public SongMetadataDto convert(@NonNull final Metadata metadata, final long id) {

        return SongMetadataDto.builder()
                .id(String.valueOf(id))
                .name(convertOrDefault(metadata, SONG_TITLE_METADATA_NAME))
                .album(convertOrDefault(metadata, SONG_ALBUM_METADATA_NAME))
                .artist(convertOrDefault(metadata, SONG_ARTIST_METADATA_NAME))
                .duration(convertDuration(metadata.get(SONG_DURATION_METADATA_NAME)))
                .year(convertOrDefault(metadata, SONG_YEAR_METADATA_NAME))
                .build();
    }

    private String convertOrDefault(final Metadata metadata, final String metadataName) {

        final String metadataValue = metadata.get(metadataName);
        return StringUtils.hasLength(metadataValue) ? metadataValue : UNKNOWN_METADATA_VALUE;
    }

    private String convertDuration(final String durationInSeconds) {

        if (!StringUtils.hasLength(durationInSeconds)) {
            return UNKNOWN_SONG_DURATION_METADATA_VALUE;
        }
        final double seconds = Double.parseDouble(durationInSeconds);

        final Duration duration = Duration.ofSeconds((long) seconds);
        final long minutes = duration.toMinutes();
        final long remainingSeconds = duration.minusMinutes(minutes).getSeconds();

        return String.format(SONG_DURATION_FORMAT, minutes, remainingSeconds);
    }
}
