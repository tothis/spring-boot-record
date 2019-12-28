package com.example.pojo;

import com.example.annotation.AutoIncKey;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@Document // 标识为mongodb文档实体
public class User implements Serializable {

    @Id // 文档编号 主键
    @AutoIncKey
    private String id;

    @Field("accountName") // 文档中用userName字段存储
    private String userName;

    @DBRef // 只保存引用 不保存具体角色信息
    private List<Role> roles;

    public User(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}