package com.example;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author 李磊
 * @datetime 2020/3/1 17:30
 * @description
 */
class SwaggerApplicationTests {

    private URL url;

    {
        try {
            url = new URL("http://localhost:8080/v2/api-docs?group=doc-dev");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成confluence格式文档
     */
    @Test
    void contextLoads() {
        generate(MarkupLanguage.CONFLUENCE_MARKUP, "src/doc/confluence", true);
        // 生成Markdown格式文档
        generate(MarkupLanguage.MARKDOWN, "src/doc/markdown", true);
        // 生成AsciiDoc格式文档
        generate(MarkupLanguage.ASCIIDOC, "src/doc/ascii-doc", true);
    }

    /**
     * 生成文档
     *
     * @param language 生成文件类型
     * @param path     保存路径
     * @param flag     是否为单个文件
     */
    private void generate(MarkupLanguage language, String path, boolean flag) {
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(language)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        Swagger2MarkupConverter build = Swagger2MarkupConverter.from(url)
                .withConfig(config)
                .build();
        if (flag)
            build.toFile(Paths.get(path));
        else
            build.toFolder(Paths.get(path));
    }
}