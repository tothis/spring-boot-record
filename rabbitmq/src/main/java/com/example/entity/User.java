package com.example.entity;

import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * amqp发送实体需实现序列号接口
 *
 * @author 李磊
 * @since 1.0
 */
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    private String userName;
}