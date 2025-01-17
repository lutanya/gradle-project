package com.epam.learn.microcervices.songservice.service;

import java.util.Collections;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.epam.learn.microcervices.songservice.dao.SongRepository;
import com.epam.learn.microcervices.songservice.dao.entity.SongMetadata;
import com.epam.learn.microcervices.songservice.dto.SongMetadataDto;
import com.epam.learn.microcervices.songservice.exception.SongMetadataAlreadyExistsException;
import com.epam.learn.microcervices.songservice.exception.SongMetadataNotFoundException;
import com.epam.learn.microcervices.songservice.service.converter.SongMetadataConverter;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongMetadataConverter songMetadataConverter;
    private final SongRepository songRepository;

    public String saveSongMetadata(@NotNull final SongMetadataDto songMetadataDto) {

        Assert.notNull(songMetadataDto, "songMetadataDto must not be null");

        final String songMetadataId = songMetadataDto.id();
        if (songRepository.findById(songMetadataId).isPresent()) {
            throw new SongMetadataAlreadyExistsException(
                    "Metadata for this ID=%s already exists.".formatted(songMetadataId));
        }
        final SongMetadata songMetadata = songMetadataConverter.toSongMetadata(songMetadataDto);
        final SongMetadata savedSongMetadata = songRepository.save(songMetadata);
        return savedSongMetadata.getId();
    }

    public SongMetadataDto findSongMetadata(@NonNull final String id) {

        Assert.notNull(id, "id must not be null");

        final SongMetadata songMetadata = songRepository.findById(id)
                .orElseThrow(() -> new SongMetadataNotFoundException(
                        "Metadata for song with ID=%s not found".formatted(id)));
        return songMetadataConverter.toSongMetadataDto(songMetadata);
    }

    public List<String> deleteSongsMetadata(@NonNull final List<String> ids) {

        Assert.notNull(ids, "ids must not be null");

        final List<String> foundSongMetadataIds = songRepository.findAllById(ids)
                .stream()
                .map(SongMetadata::getId)
                .toList();
        if (foundSongMetadataIds.isEmpty()) {
            return Collections.emptyList();
        }
        songRepository.deleteAllById(foundSongMetadataIds);
        return foundSongMetadataIds;
    }
}
