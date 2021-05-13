package com.example.model;

import com.github.dozermapper.core.Mapping;
import lombok.Data;

/**
 * @author 李磊
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