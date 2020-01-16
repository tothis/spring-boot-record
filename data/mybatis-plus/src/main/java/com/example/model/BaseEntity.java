package com.example.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 李磊
 * @datetime 2020/1/11 18:00
 * @description
 */
@Data
public class BaseEntity implements Serializable {
    private Long id;
}