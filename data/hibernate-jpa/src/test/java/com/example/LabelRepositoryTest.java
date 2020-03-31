package com.example;

import com.example.model.Label;
import com.example.model.Song;
import com.example.repository.LabelRepository;
import com.example.repository.SongRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/2/10 16:36
 * @description 多对多映射 两者都为主控方
 */
@SpringBootTest(classes = HibernateJpaApplication.class)
class LabelRepositoryTest {

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private SongRepository songRepository;

    @BeforeEach
    void before() {
        Song song1 = new Song();
        song1.setSongName("歌曲1");
        Song song2 = new Song();
        song2.setSongName("歌曲2");

        songRepository.save(song1);
        songRepository.save(song2);

        List<Song> songList = new ArrayList<Song>() {{
            add(song1);
            add(song2);
        }};

        Label label1 = new Label();
        label1.setLabelName("标签1");
        label1.setSongList(songList);
        Label label2 = new Label();
        label2.setLabelName("标签2");
        label2.setSongList(songList);

        labelRepository.save(label1);
        labelRepository.save(label2);
    }

    @AfterEach
    void after() {
        // 删除label时只会删除`label表`和`label_song表`数据 设置级联删除后 会删除song
        // labelRepository.deleteAll();
        // 删除song时只会删除`song表`和`label_song表`数据 设置级联删除后 会删除label
        songRepository.deleteAll();
    }

    @Test
    void main() {
        for (Label label : labelRepository.findAll()) {
            System.out.println(label);
        }
    }
}