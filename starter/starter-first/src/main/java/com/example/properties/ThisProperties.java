package com.example.properties;

import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置类 用于加载配置文件对应的前缀配置项
 *
 * @author 李磊
 **/
@ToString
@Setter
@ConfigurationProperties("this.profile")
public class ThisProperties {

    private String userName = "未命名";

    private int age;
}