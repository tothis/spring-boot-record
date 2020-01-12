package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李磊
 * @datetime 2020/1/10 9:52
 * @description
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 静态资源配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String os = System.getProperty("os.name");
        String folder;
        if (os.toLowerCase().indexOf("win") != -1) {
            folder = "file:D:/data/";
        } else {
            folder = "file:/data/";
        }
        System.setProperty("folder", folder);
        // 配置server虚拟路径 handler为浏览器访问路径 locations为对应的本地目录
        registry.addResourceHandler("/file/**").addResourceLocations(folder);
    }
}