package com.example.tag;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashSet;
import java.util.Set;

/**
 * Thymeleaf方言
 */
@Component
public class NumberToLetterDialect extends AbstractProcessorDialect {

    private static final String DIALECT_NAME = "dialectName";
    private static final String PREFIX = "prefix";

    public NumberToLetterDialect() {
        // 设置自定义方言与方言处理器优先级相同
        super(DIALECT_NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    @Override
    /**
     * 元素处理器
     * @param dialectPrefix 方言前缀
     * @return
     */
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        Set<IProcessor> processors = new HashSet<>();
        // 添加自定义标签处理器
        processors.add(new NumberToLetterDialectProcessor(dialectPrefix));
        processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));
        return processors;
    }
}