package com.example.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * 歌曲类
 */
@Data
@ToString(exclude = "labelList") // 防止序列化时'java.lang.StackOverflowError'
@Entity
public class Song extends BaseEntity {

    private String songName;

    // 多对多关系中一般不设置级联保存 级联删除 级联更新等操作
    // 关闭懒加载(可级联查询)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "label_song",
            joinColumns = {@JoinColumn(name = "song_id")},//本表与中间表的外键对应
            inverseJoinColumns = {@JoinColumn(name = "label_id")}
    )
    private List<Label> labelList;
}