package com.example.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李磊
 * @datetime 2020/2/10 21:19
 * @description
 */
@Data
// id类 属性类型和名称与被使用类相对应 必须实现序列化接口
public class BookUserPK implements Serializable {

    private Long bookId;

    private Long userId;
}