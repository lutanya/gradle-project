package com.epam.learn.microcervices.songservice.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "songs")
public class SongMetadata {
    @Id
    private String id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
