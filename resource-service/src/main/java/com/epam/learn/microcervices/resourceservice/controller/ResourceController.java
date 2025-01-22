package com.epam.learn.microcervices.resourceservice.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private static final String AUDIO_MPEG_MEDIA_TYPE = "audio/mpeg";
    private final ResourceService resourceService;

    @GetMapping(
            path = "/{id}",
            produces = AUDIO_MPEG_MEDIA_TYPE
    )
    public ResponseEntity<byte[]> findSong(@Valid @Positive @PathVariable final long id) {

        return ResponseEntity.ok(resourceService.findResource(id));
    }

    @PostMapping(
            consumes = AUDIO_MPEG_MEDIA_TYPE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SongUploadResponse> saveSong(@Valid @ValidMP3 @RequestBody byte[] audioData) {

        final long savedSongId = resourceService.saveResource(audioData);
        return ResponseEntity.ok(new SongUploadResponse(savedSongId));
    }

    @DeleteMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SongsDeletedResponse> deleteSongs(
            @Valid
            @ValidSongMetadataIdsList
            @RequestParam(name = "id") final List<String> ids
    ) {

        final List<Long> deletedSongsIds = resourceService.deleteResources(ids);
        return ResponseEntity.ok(new SongsDeletedResponse(deletedSongsIds));
    }

}
