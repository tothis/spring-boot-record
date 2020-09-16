package com.example.pojo;

import com.example.annotation.AutoIncKey;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李磊
 * @since 1.0
 */
@Data
// 标识为mongodb文档实体
@Document
public class User implements Serializable {

    /**
     * 文档编号 主键
     */
    @Id
    @AutoIncKey
    private Long id;

    /**
     * 文档中用userName字段存储
     */
    @Field("accountName")
    private String userName;

    /**
     * 只保存引用 不保存具体角色信息
     */
    @DBRef
    private List<Role> roles;
}