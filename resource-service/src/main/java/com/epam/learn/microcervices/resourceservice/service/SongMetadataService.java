package com.epam.learn.microcervices.resourceservice.service;

import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.springframework.lang.NonNull;

import com.epam.learn.microcervices.resourceservice.dao.entity.Resource;

public interface SongMetadataService {

    void saveSongMetadata(@NonNull final Resource resource, @NonNull final Metadata metadata);

    void deleteSongsMetadata(@NonNull final List<String> ids);
}
