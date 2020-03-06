package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author 李磊
 * @datetime 2020/2/10 21:11
 * @description
 */
@Data
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

    private String name;

    public Book(String name) {
        this.name = name;
    }
}