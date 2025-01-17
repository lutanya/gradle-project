package com.epam.learn.microcervices.resourceservice.validation;

import org.apache.tika.Tika;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMP3Validator implements ConstraintValidator<ValidMP3, byte[]> {

    private static final String MP3_MIME_TYPE = "audio/mpeg";

    @Override
    public boolean isValid(final byte[] audioData, final ConstraintValidatorContext constraintValidatorContext) {

        if (audioData == null) {
            return false;
        }
        Tika tika = new Tika();
        String detectedType = tika.detect(audioData);
        return MP3_MIME_TYPE.equals(detectedType);
    }
}
