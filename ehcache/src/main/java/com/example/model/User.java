package com.example.model;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author 李磊
 * @datetime 2019/12/26 22:19
 * @description 用户
 */
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private Byte age;

    private String mail;

    private LocalDateTime birthday;

    private String address;

    // @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createDateTime;

    // @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @Column(name = "is_del")
    @ColumnDefault("0")
    private Boolean delFlag;
}