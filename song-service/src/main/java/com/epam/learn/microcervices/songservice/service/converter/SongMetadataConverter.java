package com.epam.learn.microcervices.songservice.service.converter;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.epam.learn.microcervices.songservice.dao.entity.SongMetadata;
import com.epam.learn.microcervices.songservice.dto.SongMetadataDto;

@Component
public class SongMetadataConverter {

    @NonNull
    public SongMetadata toSongMetadata(@NonNull final SongMetadataDto songDto) {

        Assert.notNull(songDto, "songDto must not be null");

        return SongMetadata.builder()
                .id(songDto.id())
                .name(songDto.name())
                .album(songDto.album())
                .artist(songDto.artist())
                .duration(songDto.duration())
                .year(songDto.year())
                .build();
    }

    @NonNull
    public SongMetadataDto toSongMetadataDto(@NonNull final SongMetadata song) {

        Assert.notNull(song, "song must not be null");

        return SongMetadataDto.builder()
                .id(song.getId())
                .name(song.getName())
                .album(song.getAlbum())
                .artist(song.getArtist())
                .duration(song.getDuration())
                .year(song.getYear())
                .build();
    }
}
