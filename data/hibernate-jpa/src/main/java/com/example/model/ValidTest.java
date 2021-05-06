package com.example.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * @author 李磊
 */
@Data
public class ValidTest {

    public interface Update {
    }

    // @Range(min = 1, max = 120, message = "ID不合法")

    @Max(value = 120, message = "ID最大为120")
    @Min(value = 1, message = "ID最小为1")
    @NotNull(groups = Update.class, message = "ID不可为空")
    private Long id;

    private Byte age;
    @Length(min = 8, max = 16, message = "密码长度在[8-16]之间")
    @NotBlank(message = "text不能为空")
    private String text;

    /*
     * 嵌套验证需使用@Valid
     *   @Validated 可用在 [类型 方法 方法参数] 无法进行嵌套校验
     *   @Valid 可用在 [构造函数 成员属性 方法 方法参数] 可进行嵌套校验
     *
     * List校验与数组同理
     */

    @Valid
    @Size(min = 2, message = "长度最小为2")
    @NotEmpty(message = "不可为空")
    private Item[] items;

    @Data
    static class Item {
        private String text;
    }
}