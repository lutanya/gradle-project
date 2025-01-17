package com.epam.learn.microcervices.resourceservice.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.learn.microcervices.resourceservice.dto.SongUploadResponse;
import com.epam.learn.microcervices.resourceservice.dto.SongsDeletedResponse;
import com.epam.learn.microcervices.resourceservice.service.ResourceService;
import com.epam.learn.microcervices.resourceservice.service.ResourceServiceImpl;
import com.epam.learn.microcervices.resourceservice.validation.ValidMP3;
import com.epam.learn.microcervices.resourceservice.validation.ValidSongMetadataIdsList;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping(path = "/{id}", produces = "audio/mpeg")
    public byte[] findSong(@Valid @Positive @PathVariable final long id) {

        return resourceService.findResource(id);
    }

    @PostMapping(consumes = "audio/mpeg")
    public SongUploadResponse saveSong(@Valid @ValidMP3 @RequestBody byte[] audioData) {

        final long savedSongId = resourceService.saveResource(audioData);
        return new SongUploadResponse(savedSongId);
    }

    @DeleteMapping
    public SongsDeletedResponse deleteSongs(
            @Valid
            @ValidSongMetadataIdsList
            @RequestParam(name = "id") final List<String> ids
    ) {

        final List<Long> deletedSongsIds = resourceService.deleteResources(ids);
        return new SongsDeletedResponse(deletedSongsIds);
    }

}
