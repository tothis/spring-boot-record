package com.example.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 李磊
 */
@Data
public class ProductDTO {
    public String text;
    private Boolean state;
    private LocalDateTime dateTime1;
    private LocalDateTime dateTime2;
}