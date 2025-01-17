package com.epam.learn.microcervices.resourceservice.collaborator;

import java.util.List;

import org.springframework.lang.NonNull;

import com.epam.learn.microcervices.resourceservice.dto.SongMetadataDto;

public interface SongMetadataCollaborator {

    void saveSongMetadata(@NonNull final SongMetadataDto songMetadataDto);

    void deleteSongsMetadata(@NonNull final List<String> ids);
}
