package com.epam.learn.microcervices.resourceservice.service;

import java.util.List;

import org.apache.tika.metadata.Metadata;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.epam.learn.microcervices.resourceservice.dao.ResourceRepository;
import com.epam.learn.microcervices.resourceservice.dao.entity.Resource;
import com.epam.learn.microcervices.resourceservice.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final SongMetadataServiceImpl songMetadataService;
    private final ResourceRepository resourceRepository;

    @Override
    public long saveResource(@NonNull final byte[] audioData) {

        Assert.notNull(audioData, "audioData must not be null");

        final Metadata metadata = songMetadataService.extractSongMetadata(audioData);
        songMetadataService.validateMetadata(metadata);

        final Resource resource = Resource.builder()
                .content(audioData)
                .build();
        final Resource savedResource = resourceRepository.save(resource);
        songMetadataService.saveSongMetadata(savedResource, metadata);
        return savedResource.getId();
    }

    @Override
    public byte[] findResource(@NonNull final long id) {

        return resourceRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Resource with ID={} not found", id);
                    return new ResourceNotFoundException("Resource with ID=%s not found".formatted(id));
                })
                .getContent();
    }

    @Override
    public List<Long> deleteResources(@NonNull final List<String> ids) {

        Assert.notNull(ids, "ids must not be null");

        songMetadataService.deleteSongsMetadata(ids);

        final List<Long> idsL = ids.stream().map(Long::parseLong).toList();
        final List<Long> foundIdsToDelete = resourceRepository.findAllById(idsL)
                .stream()
                .map(Resource::getId)
                .toList();
        resourceRepository.deleteAllById(foundIdsToDelete);
        return foundIdsToDelete;
    }
}
