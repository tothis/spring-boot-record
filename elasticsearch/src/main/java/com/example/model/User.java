package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * indexName 索引库名称
 *
 * @author 李磊
 */
@Data
@Document(indexName = "user")
public class User {
    /**
     * 新增数据时不设置 ID，则生成默认 ID，此处必须为字符串类型
     */
    @Id
    private String id;

    private String name;

    private Date date;

    private LocalDate localDate;

    private LocalDateTime localDateTime;
}
