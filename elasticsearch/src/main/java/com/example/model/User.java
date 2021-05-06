package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * indexName 索引库名称
 *
 * @author 李磊
 */
@Data
@Document(indexName = "user")
public class User {
    @Id
    private Long id;

    @Field
    private String userName;
}