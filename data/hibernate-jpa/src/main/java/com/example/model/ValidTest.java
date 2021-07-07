package com.example.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * @author 李磊
 */
@Slf4j
// 多个条件使用 @ScriptAssert.List 或累加多个 @ScriptAssert

@ScriptAssert(script = "_this.state1 !== 0", lang = "js", message = "state1 不可为 0")
@ScriptAssert(script = "com.example.model.ValidTest.checkState(_this.state2)", lang = "js", message = "state2 不可为 0")
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

    private byte state1;
    private byte state2;

    @Data
    static class Item {
        private String text;
    }

    public static boolean checkState(byte state) {
        return state != 0;
    }

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        for (ScriptEngineFactory factory : factories) {
            log.info("\n\n引擎名称 {}\t引擎简称 {}\n\t语言名称 {}"
                    , factory.getEngineName()
                    , factory.getNames()
                    , factory.getLanguageName());
        }
    }
}
