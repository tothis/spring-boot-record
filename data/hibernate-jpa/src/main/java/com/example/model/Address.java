package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * 业务逻辑为一个用户拥有一个地址
 *
 * @author 李磊
 */
@Data
// 防止嵌套调用
@ToString(exclude = "user")
// JPA需要空参构造函数

@NoArgsConstructor
@Entity
public class Address extends BaseEntity {

    /**
     * 如不需要根据address级联查询user 可注释此处@OneToOne
     */
    @OneToOne(mappedBy = "address")
    private User user;

    private String content;

    public Address(String content) {
        this.content = content;
    }
}