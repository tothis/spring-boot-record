package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author 李磊
 * @datetime 2020/2/10 16:25
 * @description 业务逻辑为一个用户拥有一个地址
 */
@NoArgsConstructor // jpa需要空参构造函数
@Data
@Entity
public class Address {
    @Id // 标识主键
    // generator jpa中唯一标识
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "address_sq")
    @TableGenerator(name = "address_sq" // 和GeneratedValue的generator值一致
            , table = "tb_address_sq" // 生成表表名
            , pkColumnName = "description" // 表中保存描述字段名
            , pkColumnValue = "用户地址 address" // 表中保存描述值
            , valueColumnName = "sequence_value" // 表中保存主键值字段名
            , allocationSize = 1 // 自增值
    )

    private Long id;
    private String address;

    public Address(String address) {
        this.address = address;
    }
}