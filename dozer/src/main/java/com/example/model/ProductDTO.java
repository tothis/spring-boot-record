package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 李磊
 * @datetime 2020/3/5 23:33
 * @description
 */
@Data
public class ProductDTO {
    public String text;
    private Boolean state;
    private LocalDateTime dateTime1;
    private LocalDateTime dateTime2;
}