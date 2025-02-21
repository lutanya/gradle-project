package com.epam.learn.microcervices.resourceservice.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.xml.sax.SAXException;

import com.epam.learn.microcervices.resourceservice.collaborator.SongServiceCollaborator;
import com.epam.learn.microcervices.resourceservice.dao.entity.Resource;
import com.epam.learn.microcervices.resourceservice.dto.SongMetadataDto;
import com.epam.learn.microcervices.resourceservice.exception.InternalServiceException;
import com.epam.learn.microcervices.resourceservice.exception.SongMetadataInvalidException;
import com.epam.learn.microcervices.resourceservice.service.converter.SongMetadataConverter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SongMetadataServiceImpl implements SongMetadataService {

    private final SongServiceCollaborator songServiceCollaborator;
    private final SongMetadataConverter songMetadataConverter;

    @Override
    public void saveSongMetadata(@NonNull final Resource resource, @NonNull final Metadata metadata) {

        Assert.notNull(resource, "resource must not be null");

        final SongMetadataDto songMetadataDto = songMetadataConverter.convert(metadata, resource.getId());
        songServiceCollaborator.saveSongMetadata(songMetadataDto);
    }

    @Override
    public void deleteSongsMetadata(@NonNull final List<String> ids) {

        Assert.notNull(ids, "ids must not be null");

        songServiceCollaborator.deleteSongsMetadata(ids);
    }

    public Metadata extractSongMetadata(@NonNull final byte[] audioData) {

        Assert.notNull(audioData, "audioData must not be null");

        final BodyContentHandler handler = new BodyContentHandler();
        final Metadata metadata = new Metadata();
        final InputStream inputstream = new ByteArrayInputStream(audioData);
        final ParseContext context = new ParseContext();

        final Mp3Parser Mp3Parser = new Mp3Parser();
        try {
            Mp3Parser.parse(inputstream, handler, metadata, context);
            return metadata;
        } catch (IOException | SAXException | TikaException e) {
            log.error("Error occurred while audioData parsing", e);
            throw new InternalServiceException("Error occurred while audioData parsing", e);
        }
    }

    public void validateMetadata(@NonNull final Metadata metadata) {

        if (ObjectUtils.isEmpty(metadata.get(SongMetadataConverter.SONG_ALBUM_METADATA_NAME))
                || ObjectUtils.isEmpty(metadata.get(SongMetadataConverter.SONG_ARTIST_METADATA_NAME))
                || ObjectUtils.isEmpty(metadata.get(SongMetadataConverter.SONG_DURATION_METADATA_NAME))
                || ObjectUtils.isEmpty(metadata.get(SongMetadataConverter.SONG_TITLE_METADATA_NAME))
                || ObjectUtils.isEmpty(metadata.get(SongMetadataConverter.SONG_YEAR_METADATA_NAME))) {
            log.warn("Song metadata ({}) is invalid", metadata);
            throw new SongMetadataInvalidException("Song metadata (%s) is invalid".formatted(metadata));
        }
    }
}
