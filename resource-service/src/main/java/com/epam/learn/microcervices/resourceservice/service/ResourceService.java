package com.epam.learn.microcervices.resourceservice.service;

import java.util.List;

import org.springframework.lang.NonNull;

public interface ResourceService {

    long saveResource(@NonNull final byte[] audioData);

    byte[] findResource(@NonNull final long id);

    List<Long> deleteResources(@NonNull final List<String> ids);

}
