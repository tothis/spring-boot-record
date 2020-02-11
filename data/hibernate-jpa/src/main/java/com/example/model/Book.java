package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author 李磊
 * @datetime 2020/2/10 21:11
 * @description
 */
@Data
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Book(String name) {
        this.name = name;
    }
}