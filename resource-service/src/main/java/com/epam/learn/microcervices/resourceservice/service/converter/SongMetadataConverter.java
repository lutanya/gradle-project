package com.epam.learn.microcervices.resourceservice.service.converter;

import java.time.Duration;

import org.apache.tika.metadata.Metadata;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.epam.learn.microcervices.resourceservice.dto.SongMetadataDto;

@Component
public class SongMetadataConverter {

    public static final String SONG_ALBUM_METADATA_NAME = "xmpDM:album";
    public static final String SONG_ARTIST_METADATA_NAME = "xmpDM:artist";
    public static final String SONG_DURATION_FORMAT = "%02d:%02d";
    public static final String SONG_DURATION_METADATA_NAME = "xmpDM:duration";
    public static final String SONG_TITLE_METADATA_NAME = "dc:title";
    public static final String SONG_YEAR_METADATA_NAME = "xmpDM:releaseDate";

    @NonNull
    public SongMetadataDto convert(@NonNull final Metadata metadata, final long id) {

        return SongMetadataDto.builder()
                .id(String.valueOf(id))
                .name(convert(metadata, SONG_TITLE_METADATA_NAME))
                .album(convert(metadata, SONG_ALBUM_METADATA_NAME))
                .artist(convert(metadata, SONG_ARTIST_METADATA_NAME))
                .duration(convertDuration(metadata.get(SONG_DURATION_METADATA_NAME)))
                .year(convert(metadata, SONG_YEAR_METADATA_NAME))
                .build();
    }

    private String convert(final Metadata metadata, final String metadataName) {

        return metadata.get(metadataName);
    }

    private String convertDuration(final String durationInSeconds) {

        if (!StringUtils.hasLength(durationInSeconds)) {
            return durationInSeconds;
        }
        final double seconds = Double.parseDouble(durationInSeconds);

        final Duration duration = Duration.ofSeconds((long) seconds);
        final long minutes = duration.toMinutes();
        final long remainingSeconds = duration.minusMinutes(minutes).getSeconds();

        return String.format(SONG_DURATION_FORMAT, minutes, remainingSeconds);
    }
}
