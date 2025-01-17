package com.epam.learn.microcervices.songservice.dao;

import org.springframework.data.repository.ListCrudRepository;

import com.epam.learn.microcervices.songservice.dao.entity.SongMetadata;

public interface SongRepository extends ListCrudRepository<SongMetadata, String> {

}
