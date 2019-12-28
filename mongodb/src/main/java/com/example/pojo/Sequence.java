package com.example.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 模拟序列类
 *
 * @author 李磊
 * @time 2019/11/10 17:52
 */
@Data
@Document("sequence")
public class Sequence {

    @Id
    private String id; // 主键

    @Field
    private String collName; // 集合名称

    @Field
    private Long sequenceId; // 序列值
}