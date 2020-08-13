package com.example.model;

import lombok.Data;

import java.util.List;

/**
 * @author 李磊
 * @datetime 2020/3/2 0:38
 * @description
 */
@Data
public class Tree {
    private Long id;
    private Long parentId;
    private String name;
    private List<Tree> trees;
}