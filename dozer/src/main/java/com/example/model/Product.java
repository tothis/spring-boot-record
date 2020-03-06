package com.example.model;

import com.github.dozermapper.core.Mapping;
import lombok.Data;

/**
 * @author 李磊
 * @datetime 2020/3/5 23:01
 * @description
 */
@Data
public class Product {
    @Mapping("text")
    public String name;
    @Mapping("state")
    private Byte flag;
    private String dateTime1;
    private String dateTime2;
}