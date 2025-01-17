package com.epam.learn.microcervices.songservice.validation;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ValidationConstants {
    public static final String SONG_METADATA_ID_PATTERN = "[1-9]\\d*";
    public static final String SONG_METADATA_DURATION_PATTERN = "^([01]\\d|2[0-3]):([0-5]\\d)$";
    public static final String SONG_METADATA_YEAR_PATTERN = "^(19\\d\\d|20\\d\\d)$";
}
