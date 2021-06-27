package com.example.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本实体类
 *
 * @author 李磊
 */
@Data
public class BaseEntity implements Serializable {

    private Long id;

    /**
     * 标识 0正常(默认) 1删除
     */
    private Byte state;
}
