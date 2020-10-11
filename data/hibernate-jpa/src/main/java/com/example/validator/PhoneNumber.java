package com.example.validator;

import com.example.util.RegUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验手机号
 *
 * @author 李磊
 * @since 1.0
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PhoneNumber.DateValidatorInner.class)
public @interface PhoneNumber {

    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Type value() default Type.ALL;

    /**
     * 校验类型
     */
    enum Type {
        ALL, HK, CN
    }

    class DateValidatorInner implements ConstraintValidator<PhoneNumber, Long> {
        private Type range;

        @Override
        public void initialize(PhoneNumber constraintAnnotation) {
            this.range = constraintAnnotation.value();
        }

        @Override
        public boolean isValid(Long value, ConstraintValidatorContext context) {
            String phoneNumber = String.valueOf(value);
            switch (range) {
                case CN:
                    return RegUtil.isChinaPhoneNumber(phoneNumber);
                case HK:
                    return RegUtil.isHKPhoneNumber(phoneNumber);
                case ALL:
                    return RegUtil.isPhoneNumber(phoneNumber);
                default:
                    return false;
            }
        }
    }
}