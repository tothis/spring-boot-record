//package com.example.config;
//
//import org.hibernate.validator.HibernateValidator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.validation.Validation;
//import javax.validation.Validator;
//
///**
// * @author 李磊
// * @datetime 2020/1/16 23:27
// * @description
// */
//@Configuration
//public class ValidatorConfig {
//    @Bean
//    public Validator validator() {
//        return Validation.byProvider(HibernateValidator.class)
//                .configure()
//                /**
//                 * 普通模式 校验完所有的属性 返回所有的验证失败信息
//                 * 快速失败返回模式 有一个验证失败立即返回失败信息
//                 */
//                // 配置hibernate Validator为快速失败返回
//                // .addProperty("hibernate.validator.fail_fast", "true")
//                .buildValidatorFactory().getValidator();
//    }
//}