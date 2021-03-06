package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 李磊
 */
@AllArgsConstructor
@Data
public class User {
    private Long id;
    /**
     * 名称
     */
    private String name;
}
