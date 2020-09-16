package com.example.validator;

import cn.hutool.core.date.DateUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Date;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验日期不小于指定天后的当前时间
 *
 * @author 李磊
 * @since 1.0
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = FutureDate.DateValidatorInner.class)
public @interface FutureDate {

    /**
     * 必有属性 显示校验信息
     * 可使用{}获取属性值
     *
     * @see org.hibernate.validator
     */
    String message() default "日期最早为{value}天后的当前时间";

    /**
     * 必有属性 用于分组校验
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 接收参收
     */
    int value() default 0;

    /**
     * 必须实现ConstraintValidator接口
     */
    class DateValidatorInner implements ConstraintValidator<FutureDate, Date> {
        private int range;

        @Override
        public void initialize(FutureDate constraintAnnotation) {
            this.range = constraintAnnotation.value();
        }

        /**
         * 校验逻辑的实现
         *
         * @param value 需要校验的值
         * @return 布尔值结果
         */
        @Override
        public boolean isValid(Date value, ConstraintValidatorContext context) {
            if (range == 0) {
                return true;
            }

            // 指定天后的当前时间是否小于value
            return DateUtil.offsetDay(DateUtil.date(), range)
                    .toJdkDate().before(value);
        }
    }
}