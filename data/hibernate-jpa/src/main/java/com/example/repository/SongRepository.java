package com.example.repository;

import com.example.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李磊
 */
public interface SongRepository extends JpaRepository<Song, Long> {
}
