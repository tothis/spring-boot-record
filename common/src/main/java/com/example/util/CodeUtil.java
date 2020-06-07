package com.example.util;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class CodeUtil {
    public static void main(String[] args) {
        // 创建SpEL表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 解析表达式
        Expression exp = parser.parseExpression("'li' + 'lei'");
        // 取出解析结果
        String result = exp.getValue().toString();
        // 输出结果
        System.out.println(result);
    }
}