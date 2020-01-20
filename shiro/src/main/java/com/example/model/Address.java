package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author 李磊
 * @datetime 2020/1/19 20:21
 * @description
 */
@ApiModel(value = "com.example.model.Address", description = "地址")
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @Column(name = "detail", columnDefinition = "varchar(40) COMMENT '详情'")
    private String detail;
}