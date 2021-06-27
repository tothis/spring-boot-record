package com.example.model;

import lombok.Data;

/**
 * 数据库名称
 *
 * @author 李磊
 */
@Data
public class Table {
    private String id;
    private String url;
    private String userName;
    private String password;
}
