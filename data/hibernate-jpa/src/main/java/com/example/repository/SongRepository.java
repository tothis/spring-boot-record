package com.example.repository;

import com.example.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 李磊
 * @datetime 2020/2/10 21:21
 * @description
 */
public interface SongRepository extends JpaRepository<Song, Long> {
}