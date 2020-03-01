package com.example.tag;

import com.example.util.Util;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 参考文档
 * https://www.thymeleaf.org/doc/tutorials/3.0/extendingthymeleaf.html#expression-object-dialects-iexpressionobjectdialect
 */
@Component
public class UtilDialect implements IExpressionObjectDialect {

    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        return new IExpressionObjectFactory() {

            @Override
            public Set<String> getAllExpressionObjectNames() {
                // 可为自定义工具类起多个名称 一个名称初始化一个对象
                return new HashSet<String>() {{
                    add("util1");
                    add("util2");
                }};
            }

            @Override
            public Object buildObject(IExpressionContext context, String expressionObjectName) {
                // System.out.println(expressionObjectName); // util名称
                return new Util();
            }

            @Override
            public boolean isCacheable(String expressionObjectName) {
                return true;
            }
        };
    }

    @Override
    public String getName() {
        return "util";
    }
}