package com.example.model;

import lombok.Data;

import java.util.List;

/**
 * @author 李磊
 */
@Data
public class Tree {
    private Long id;
    private Long parentId;
    private String name;
    private List<Tree> trees;
}