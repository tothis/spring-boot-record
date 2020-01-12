package com.example.tag;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李　磊
 * 标签处理器
 */
public class NumberToLetterDialectProcessor extends AbstractAttributeTagProcessor {
    // 标签名
    private static final String TAG_NAME = "tagName";
    // 属性名
    private static final String ATTRIBUTE_NAME = "attributeName";

    protected NumberToLetterDialectProcessor(String dialectPrefix) {
        super(
                // 此处理器将仅应用于HTML模式
                TemplateMode.HTML,
                // 标签前缀名 xxxx:text中的xxxx
                dialectPrefix,
                // 标签名称 匹配此名称的特定标签 null为不限制
                TAG_NAME,
                // 将标签前缀应用于标签名称
                false, // true
                // 标签前缀属性 如<xxxx:toLetter>
                ATTRIBUTE_NAME,
                // 开启属性名称前缀
                true,
                // 优先级和原生标签相同
                StandardDialect.PROCESSOR_PRECEDENCE,
                // 标签处理后是否移除自定义属性
                true);
    }

    @Override
    protected void doProcess(ITemplateContext context
            , IProcessableElementTag tag
            , AttributeName attributeName
            , String attributeValue
            , IElementTagStructureHandler structureHandler) {

        // 获取属性值
        final IEngineConfiguration configuration = context.getConfiguration();
        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = parser.parseExpression(context, attributeValue);
        final String rawValue = String.valueOf(expression.execute(context));

        // 获取标签名
        // final String elementCompleteName = tag.getElementCompleteName();
        // 创建模型
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();
        // 添加模型 标签
        model.add(modelFactory.createOpenElementTag("h1"));
        model.add(modelFactory.createText(NumberToLetterConverter(rawValue)));
        model.add(modelFactory.createCloseElementTag("h1"));
        // 替换页面标签
        structureHandler.replaceWith(model, false);

        // 只改变标签内数据
        // structureHandler.setBody(NumberToLetterConverter(rawValue), false);
    }

    private String NumberToLetterConverter(String str) {
        int i = Integer.valueOf(str);
        char c;
        String s = "";
        if (i <= 26) {
            // 将ASCII码转换成字母 我这里都是小写
            c = (char) (i + 96);
            s = String.valueOf(c);
        } else if (i > 26) {
            List<Character> numlist = new ArrayList<>();
            // 单循环数大于26时 就在前加个啊 效果 27 : aa
            int num = i / 26;
            for (int a = 0; a < num; a++) {
                numlist.add('a');
            }
            numlist.add((char) (i % 26 + 97));
            for (Character character : numlist) {
                s = s + new StringBuilder().append(character).toString();
            }
        }
        return s.toUpperCase();
    }
}