package com.epam.learn.microcervices.songservice.controller;

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

import com.epam.learn.microcervices.songservice.dto.SongMetadataDto;
import com.epam.learn.microcervices.songservice.dto.SongMetadataSavedResponse;
import com.epam.learn.microcervices.songservice.dto.SongsMetadataDeletedResponse;
import com.epam.learn.microcervices.songservice.service.SongServiceImpl;
import com.epam.learn.microcervices.songservice.validation.ValidSongMetadataIdsList;
import static com.epam.learn.microcervices.songservice.validation.ValidationConstants.SONG_METADATA_ID_PATTERN;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongServiceImpl songService;

    @GetMapping("/{id}")
    public SongMetadataDto findSongMetadata(
            @Valid
            @Pattern(regexp = SONG_METADATA_ID_PATTERN, message = "id must be numeric string")
            @PathVariable final String id) {

        return songService.findSongMetadata(id);
    }

    @PostMapping
    public SongMetadataSavedResponse saveSongMetadata(@Valid @RequestBody final SongMetadataDto songMetadata) {

        final String savedSongMetadataId = songService.saveSongMetadata(songMetadata);
        return new SongMetadataSavedResponse(savedSongMetadataId);
    }

    @DeleteMapping
    public SongsMetadataDeletedResponse deleteSongsMetadata(
            @Valid
            @ValidSongMetadataIdsList
            @RequestParam(name = "id") final List<String> ids) {

        final List<String> deletedSongsIds = songService.deleteSongsMetadata(ids);
        return new SongsMetadataDeletedResponse(deletedSongsIds);
    }
}
