package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * @author 李磊
 * @datetime 2020/2/10:15
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
// 一个实体有多个主键字段时 jpa需要定义id类
@IdClass(BookUserPK.class)
public class BookUser {
    @Id
    private Long bookId;

    @Id
    private Long userId;
}