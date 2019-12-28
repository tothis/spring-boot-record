package com.example.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document
public class Role {

    @Id
    private Integer id;

    @Field("roleName")
    private String roleName;
}