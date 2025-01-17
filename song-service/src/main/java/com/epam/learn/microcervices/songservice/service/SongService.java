package com.epam.learn.microcervices.songservice.service;

import java.util.List;

import org.springframework.lang.NonNull;

import com.epam.learn.microcervices.songservice.dto.SongMetadataDto;

import jakarta.validation.constraints.NotNull;

public interface SongService {

    String saveSongMetadata(@NotNull final SongMetadataDto songMetadataDto);

    SongMetadataDto findSongMetadata(@NonNull final String id);

    List<String> deleteSongsMetadata(@NonNull final List<String> ids);
}
