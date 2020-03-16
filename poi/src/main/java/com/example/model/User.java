package com.example.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李磊
 * @datetime 2020/3/14 17:16
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Excel(name = "id")
    private Long id;
    @Excel(name = "姓名", orderNum = "1")
    private String userName;
}