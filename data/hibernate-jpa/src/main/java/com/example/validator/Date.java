package com.example.validator;

import com.example.util.DateUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.format.DateTimeParseException;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 李磊
 * @datetime 2020/1/17 12:26
 * @description
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = Date.DateValidatorInner.class)
public @interface Date {

    /**
     * 必有属性 显示校验信息
     * 可使用{}获取属性值
     *
     * @see org.hibernate.validator
     */
    String message() default "日期格式不匹配[{value}]";

    /**
     * 必有属性 用于分组校验
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 接收参收
     */
    String value() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 必须实现ConstraintValidator接口
     */
    class DateValidatorInner implements ConstraintValidator<Date, String> {
        private String dateFormat;

        @Override
        public void initialize(Date constraintAnnotation) {
            this.dateFormat = constraintAnnotation.value();
        }

        /**
         * 校验逻辑的实现
         *
         * @param value 需要校验的值
         * @return 布尔值结果
         */
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null || value.isEmpty()) {
                return true;
            }
            try {
                return DateUtil.dateTime(value, dateFormat) != null;
            } catch (DateTimeParseException e) {
                return false;
            }
        }
    }
}
