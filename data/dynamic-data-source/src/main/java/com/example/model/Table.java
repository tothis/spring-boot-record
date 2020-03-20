package com.example.model;

import lombok.Data;

/**
 * @author 李磊
 * @datetime 2020/3/19 9:37
 * @description 数据库名称
 */
@Data
public class Table {
    private String id;
    private String url;
    private String userName;
    private String password;
}