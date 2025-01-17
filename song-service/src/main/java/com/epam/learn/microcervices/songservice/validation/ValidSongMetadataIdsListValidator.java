package com.epam.learn.microcervices.songservice.validation;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.util.CollectionUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.epam.learn.microcervices.songservice.validation.ValidationConstants.SONG_METADATA_ID_PATTERN;

public class ValidSongMetadataIdsListValidator implements ConstraintValidator<ValidSongMetadataIdsList, List<String>> {

    private final static int MAX_IDS_LIST_LENGTH = 200;

    @Override
    public boolean isValid(final List<String> ids, final ConstraintValidatorContext constraintValidatorContext) {

        return !CollectionUtils.isEmpty(ids) && isValidIdsListLength(ids) && isValid(ids);
    }

    private boolean isValid(final List<String> ids) {

        return ids
                .stream()
                .allMatch(id -> Pattern.matches(SONG_METADATA_ID_PATTERN, id));
    }

    private boolean isValidIdsListLength(final List<String> ids) {

        final String idsString = String.join(",", ids);
        return idsString.length() < MAX_IDS_LIST_LENGTH;
    }
}
